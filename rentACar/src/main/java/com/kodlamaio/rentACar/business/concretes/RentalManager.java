package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.FindexScoreCheckService;
import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.request.rentals.CreateRentalRequest;
import com.kodlamaio.rentACar.business.request.rentals.DeleteRentalRequest;
import com.kodlamaio.rentACar.business.request.rentals.UpdateRentalRequest;
import com.kodlamaio.rentACar.business.responses.rentals.ListRentalResponse;
import com.kodlamaio.rentACar.business.responses.rentals.RentalResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.ErrorResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.UserRepository;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Rental;
import com.kodlamaio.rentACar.entities.concretes.User;
@Service
public class RentalManager implements RentalService {

	RentalRepository rentalRepository;
	CarRepository carRepository;
	ModelMapperService modelMapperService;
	UserRepository userRepository;
	@Autowired
	FindexScoreCheckService findexScoreCheckService;
	


	public RentalManager(RentalRepository rentalRepository,CarRepository carRepository,ModelMapperService modelMapperService, UserRepository userRepository) {

		this.rentalRepository = rentalRepository;
		this.carRepository = carRepository;
		this.modelMapperService = modelMapperService ;
		this.userRepository = userRepository;
		
		
	}

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {
		
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		Car car = this.carRepository.findById(createRentalRequest.getCarId());
		User user = this.userRepository.getById(createRentalRequest.getUserId());
		if (checkMinFindexScore(car.getMinFindexScore(), user.getIdentityNumber())) {
			
		car.setState(3);
		
		long range = calculateTotalDays(rental);
		double totalPrice = range*car.getDailyPrice();
		if (rental.getPickupCityId()!=rental.getReturnCityId()) {
			totalPrice = totalPrice+ 750;
			}
		rental.setTotalPrice(totalPrice);
		rental.setCar(car);
		this.rentalRepository.save(rental);
		return new SuccessResult("RENTAL ADDED");	
		}
		else {
			return new ErrorResult("CAR CANNOT RENTED");
		}
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest) {

		Rental rentalToUpdate = this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		Car car = this.carRepository.findById(updateRentalRequest.getCarId());

		long range = calculateTotalDays(rentalToUpdate);
		double totalPrice = range * car.getDailyPrice();
		if (rentalToUpdate.getPickupCityId() != rentalToUpdate.getReturnCityId()) {
			totalPrice = totalPrice + 750;
		}
		rentalToUpdate.setTotalPrice(totalPrice);
		rentalToUpdate.setCar(car);
		this.rentalRepository.save(rentalToUpdate);

		return new SuccessResult("RENTAL.UPDATED");
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
		//new SuccessDataResult<List<Rental>>(rentalRepository.findAll());
	}

	@Override
	public DataResult<RentalResponse> getById(int id) {
		
		Rental rental = this.rentalRepository.findById(id);
		
		RentalResponse response  = this.modelMapperService.forResponse().map(rental,RentalResponse.class);

		return new SuccessDataResult<RentalResponse>(response,"RENTAL.GETTED") ;
		//new SuccessDataResult<Rental>(rentalRepository.findById(rentalResponse.getId()));
	}
	
	private long calculateTotalDays(Rental rental){
		long dayDifference = (rental.getReturnDate().getTime()-rental.getPickupDate().getTime());
		long item = TimeUnit.DAYS.convert(dayDifference, TimeUnit.MILLISECONDS);
		rental.setTotalDays((int)item);
		
		return item;
		
	}
	
	boolean checkMinFindexScore(int score, String identityNumber) {
		boolean instant = false;
		if (findexScoreCheckService.checkFindexScore(identityNumber) >score) {
			instant =true;
			
		}
		
		
		return instant;
		
	}

}

