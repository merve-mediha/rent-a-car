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
import com.kodlamaio.rentACar.dataAccess.abstracts.IndividualCustomerRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.UserRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
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
	FindexScoreCheckService findexScoreCheckService;
	
	

	@Autowired
	public RentalManager(RentalRepository rentalRepository,CarRepository carRepository,ModelMapperService modelMapperService, UserRepository userRepository,FindexScoreCheckService findexScoreCheckService,IndividualCustomerRepository individualCustomerRepository) {

		this.rentalRepository = rentalRepository;
		this.carRepository = carRepository;
		this.modelMapperService = modelMapperService ;
		this.userRepository = userRepository;
		this.findexScoreCheckService=findexScoreCheckService;
		this.individualCustomerRepository = individualCustomerRepository;
		
		
	}

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {
		
		checkIfCarState(createRentalRequest.getCarId());
		checkUserFindexScore(createRentalRequest);
		checkDateToRentACar(createRentalRequest.getPickupDate(), createRentalRequest.getReturnDate());
		
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		Car car = this.carRepository.findById(createRentalRequest.getCarId());
		
		
		int diffDate = (int) ChronoUnit.DAYS.between(rental.getPickupDate(), rental.getReturnDate());
		rental.setTotalDays(diffDate);
		double totalPrice = calculateTotalPrice(rental, car.getDailyPrice());
		 
		rental.setPickupCityId(car.getCity());
		rental.setTotalPrice(totalPrice);
		
		rentalRepository.save(rental);
		return new SuccessResult("RENTAL ADDED");
		
	
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) {
		
		checkIfCarState(updateRentalRequest.getCarId());
		checkDateToRentACar(updateRentalRequest.getPickupDate(), updateRentalRequest.getReturnDate());
		
		
		
		Rental rentalToUpdate = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		Car car = this.carRepository.findById(updateRentalRequest.getCarId());


		int diffDate = (int) ChronoUnit.DAYS.between(rentalToUpdate.getPickupDate(), rentalToUpdate.getReturnDate());
		rentalToUpdate.setTotalDays(diffDate);
		double totalPrice = calculateTotalPrice(rentalToUpdate, car.getDailyPrice());
		 
		rentalToUpdate.setPickupCityId(car.getCity());
		rentalToUpdate.setTotalPrice(totalPrice);
		
		rentalRepository.save(rentalToUpdate);
		return new SuccessResult("RENTAL UPDATED");
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		Rental rental = new Rental();
		rental.setId(deleteRentalRequest.getId());
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
	
	private double isDiffReturnCityFromPickUpCity(int pickUpCity, int returnCity) {
		if (pickUpCity!=returnCity) {
			return 750.0;
		}
		return 0;
	}
	
	private double calculateTotalPrice(Rental rental, double dailyPrice) {
		double days = rental.getTotalDays();
		double totalDailyPrice = days* dailyPrice;
		double diffCityPrice  = isDiffReturnCityFromPickUpCity(rental.getPickupCityId().getId(), rental.getReturnCityId().getId());
		double totalPrice = totalDailyPrice + diffCityPrice;
		return totalPrice;
		
	}
	
	private void checkIfCarState(int id) {
		Car car = this.carRepository.findById(id);
		if(car.getState()==2|| car.getState()==3) {
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
	
	
	private void checkUserFindexScore(CreateRentalRequest createRentalRequest) {
		Car car = this.carRepository.findById(createRentalRequest.getCarId());
		IndividualCustomer user = this.individualCustomerRepository.findById(createRentalRequest.getIndividualCustomerId()).get();
		if (findexScoreCheckService.CheckIfCorrectPerso(user.getIdentityNumber()) > car.getMinFindexScore()) {
			throw new BusinessException("USER.IS.NOT.ENO");
		}
		
	}

}

