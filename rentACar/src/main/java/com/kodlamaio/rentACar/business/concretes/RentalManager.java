package com.kodlamaio.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.request.rentals.CreateRentalRequest;
import com.kodlamaio.rentACar.business.request.rentals.DeleteRentalRequest;
import com.kodlamaio.rentACar.business.request.rentals.UpdateRentalRequest;
import com.kodlamaio.rentACar.business.responses.rentals.ListRentalResponse;
import com.kodlamaio.rentACar.business.responses.rentals.RentalResponse;
import com.kodlamaio.rentACar.core.utilities.adapters.abstracts.FindexScoreCheckService;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.ErrorResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.CorporateCustomerRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.IndividualCustomerRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.UserRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.CorporateCustomer;
import com.kodlamaio.rentACar.entities.concretes.IndividualCustomer;
import com.kodlamaio.rentACar.entities.concretes.Rental;
import com.kodlamaio.rentACar.entities.concretes.User;
@Service
public class RentalManager implements RentalService {

	RentalRepository rentalRepository;
	CarRepository carRepository;
	ModelMapperService modelMapperService;
	UserRepository userRepository;
	IndividualCustomerRepository individualCustomerRepository;
	CorporateCustomerRepository corporateCustomerRepository;
	FindexScoreCheckService findexScoreCheckService;
	
	

	@Autowired
	public RentalManager(RentalRepository rentalRepository,CarRepository carRepository,ModelMapperService modelMapperService, 
			UserRepository userRepository,FindexScoreCheckService findexScoreCheckService,
			IndividualCustomerRepository individualCustomerRepository,CorporateCustomerRepository corporateCustomerRepository) {

		this.rentalRepository = rentalRepository;
		this.carRepository = carRepository;
		this.modelMapperService = modelMapperService ;
		this.userRepository = userRepository;
		this.findexScoreCheckService=findexScoreCheckService;
		this.individualCustomerRepository = individualCustomerRepository;
		this.corporateCustomerRepository = corporateCustomerRepository;
		
		
	}

	@Override
	public Result addIndividualCustomer(CreateRentalRequest createRentalRequest) {
		
		checkIfCarState(createRentalRequest.getCarId());
		checkDateToRentACar(createRentalRequest.getPickupDate(), createRentalRequest.getReturnDate());
		checkUserFindexScore(createRentalRequest.getCarId(), createRentalRequest.getCustomerId());
		checkIndividualCustomerExists(createRentalRequest.getCustomerId());
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		rental.setTotalDays(calculateTotalDays(rental));
		rental.setTotalPrice(calculateTotalPrice(rental));
		rental.getCar().setState(3);

		rentalRepository.save(rental);
		return new SuccessResult("RENTAL ADDED");
	
	}
	
	@Override
	public Result addCorporateCustomer(CreateRentalRequest createRentalRequest) {
		checkIfCarState(createRentalRequest.getCarId());
		checkDateToRentACar(createRentalRequest.getPickupDate(), createRentalRequest.getReturnDate());
		checkCorporateCustomerExists(createRentalRequest.getCustomerId());
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		rental.setTotalDays(calculateTotalDays(rental));
		rental.setTotalPrice(calculateTotalPrice(rental));
		rental.getCar().setState(3);

		rentalRepository.save(rental);
		return new SuccessResult("RENTAL ADDED");
	}
	
	

	@Override
	public Result updateIndividualCustomer(UpdateRentalRequest updateRentalRequest) {
		checkIfRentalExists(updateRentalRequest.getId());
		checkDateToRentACar(updateRentalRequest.getPickupDate(), updateRentalRequest.getReturnDate());
		checkUserFindexScore(updateRentalRequest.getCarId(), updateRentalRequest.getCustomerId());
		checkIndividualCustomerExists(updateRentalRequest.getCustomerId());
		Car car = this.carRepository.findById(updateRentalRequest.getCarId());
		checkRentalStateFromUpdate(car);
		car.setState(3);
		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		rental.setTotalDays(calculateTotalDays(rental));
		rental.setTotalPrice(calculateTotalPrice(rental));

		rentalRepository.save(rental);
		return new SuccessResult("RENTAL UPDATED");
	}
	
	@Override
	public Result updateCorporateCustomer(UpdateRentalRequest updateRentalRequest) {
		checkIfRentalExists(updateRentalRequest.getId());
		checkDateToRentACar(updateRentalRequest.getPickupDate(), updateRentalRequest.getReturnDate());
		checkUserFindexScore(updateRentalRequest.getCarId(), updateRentalRequest.getCustomerId());
		checkCorporateCustomerExists(updateRentalRequest.getCustomerId());
		Car car = this.carRepository.findById(updateRentalRequest.getCarId());
		checkRentalStateFromUpdate(car);
		Rental rental = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		rental.setTotalDays(calculateTotalDays(rental));
		rental.setTotalPrice(calculateTotalPrice(rental));

		rentalRepository.save(rental);
		return new SuccessResult("RENTAL UPDATED");
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		checkIfRentalExists(deleteRentalRequest.getId());
		Rental rental = this.rentalRepository.findById(deleteRentalRequest.getId());
		this.rentalRepository.delete(rental);
		return new SuccessDataResult<Rental>("RENTAL.DELETED"+rental.getId());
	}

	@Override
	public DataResult<List<ListRentalResponse>> getall() {
		List<Rental> rentals = this.rentalRepository.findAll();
		
		List<ListRentalResponse> response = rentals.stream()
				.map(rental -> this.modelMapperService.forResponse().map(rental, ListRentalResponse.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ListRentalResponse>>(response,"RENTALS.GETTED");
		
	}

	@Override
	public DataResult<RentalResponse> getById(int id) {
		
		Rental rental = this.rentalRepository.findById(id);
		
		RentalResponse response  = this.modelMapperService.forResponse().map(rental,RentalResponse.class);

		return new SuccessDataResult<RentalResponse>(response,"RENTAL.GETTED") ;
	
	}
	/////////////////////////////////////////////////////////////////
	private void checkIfRentalExists(int rentalId) {
		Rental rental = this.rentalRepository.findById(rentalId);	
		if (rental == null) {
			throw new BusinessException("THERE.IS.NOT.THIS.RENTAL");
		}
	}
	
	private void checkIndividualCustomerExists(int individualCustomerId) {
		IndividualCustomer individualCustomer = this.individualCustomerRepository.findById(individualCustomerId);
		if (individualCustomer == null) {
			throw new BusinessException("THERE IS NOT INDIVIDUAL USER");
		}
	}
	private void checkCorporateCustomerExists(int corporateCustomerId) {
		CorporateCustomer corporateCustomer = this.corporateCustomerRepository.findById(corporateCustomerId);
		if (corporateCustomer == null) {
			throw new BusinessException("THERE IS NOT CORPORATE USER");
		}
	}
	
	
	private double isDiffReturnCityFromPickUpCity(int pickUpCity, int returnCity) {
		if (pickUpCity!=returnCity) {
			return 750.0;
		}
		return 0;
	}
	
	private double calculateTotalPrice(Rental rental) {
		double days = rental.getTotalDays();
		Car car = this.carRepository.findById(rental.getCar().getId());
		double totalDailyPrice = days* car.getDailyPrice();
		double diffCityPrice  = isDiffReturnCityFromPickUpCity(rental.getPickupCityId().getId(), rental.getReturnCityId().getId());
		double totalPrice = totalDailyPrice + diffCityPrice;
		return totalPrice;
		
	}
	
	
	
	private void checkIfCarState(int id) {
		Car currentCar = this.carRepository.findById(id);
		if(currentCar == null) {
			throw new BusinessException("THIS CAR IS NOT EXIST");
		}
		if(currentCar.getState()==2|| currentCar.getState()==3) {
			throw new BusinessException("CAR IS NOT AVAILABLE");
		}
		
	}
	private void checkDateToRentACar(LocalDate pickupDate, LocalDate returnDate) {
		if(!pickupDate.isBefore(returnDate)|| pickupDate.isBefore(LocalDate.now())) {
			throw new BusinessException("THIS DATE NOT AVAILABLE FOR RENTAL");
		}
	}

	@Override
	public Result updateState(int carId) {
		Car car = carRepository.findById(carId);
		if(car.getState()==1) {
			car.setState(2);
		}else {
			car.setState(1);
		}
		carRepository.save(car);
		return new SuccessResult("STATE UPDATED");
	}
	
	
	private void checkUserFindexScore(int carId, int individualCustomerId) {
		Car car = this.carRepository.findById(carId);
		IndividualCustomer user = this.individualCustomerRepository.findById(individualCustomerId);
		if (findexScoreCheckService.CheckIfCorrectPerson(user.getIdentityNumber()) > car.getMinFindexScore()) {
			throw new BusinessException("USER HAS NOT ENOUGH FINDEX SCORE  "+findexScoreCheckService.CheckIfCorrectPerson(user.getIdentityNumber()));
		}
	}
	
	private int calculateTotalDays(Rental rental) {
		int totalDays =(int) ChronoUnit.DAYS.between(rental.getPickupDate(), rental.getReturnDate());
		return totalDays;
	}
	
	private void checkRentalStateFromUpdate(Car newCar) {
		Car oldCar = this.carRepository.findById(newCar.getId());
		if (newCar.getState() != oldCar.getState()) {
			checkIfCarState(newCar.getId());
			oldCar.setState(1);		
		}
	}

	


	
	
	

}

