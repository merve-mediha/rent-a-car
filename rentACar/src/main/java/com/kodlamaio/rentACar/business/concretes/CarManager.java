package com.kodlamaio.rentACar.business.concretes;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.CarService;
import com.kodlamaio.rentACar.business.request.cars.CreateCarRequest;
import com.kodlamaio.rentACar.business.request.cars.DeleteCarRequest;
import com.kodlamaio.rentACar.business.request.cars.UpdateCarRequest;
import com.kodlamaio.rentACar.business.responses.cars.CarResponse;
import com.kodlamaio.rentACar.business.responses.cars.ListCarResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.BrandRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.CarRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.ColorRepository;
import com.kodlamaio.rentACar.entities.concretes.Brand;
import com.kodlamaio.rentACar.entities.concretes.Car;
import com.kodlamaio.rentACar.entities.concretes.Color;
@Service
public class CarManager implements CarService {

	
	private CarRepository carRepository;
	private ModelMapperService modelMapperService;
	private BrandRepository brandRepository;
	private ColorRepository colorRepository;
	
	
	
	@Autowired
	public CarManager(CarRepository carRepository, ModelMapperService modelMapperService,BrandRepository brandRepository,ColorRepository colorRepository) {
		this.carRepository = carRepository;
		this.modelMapperService = modelMapperService;
		this.brandRepository = brandRepository;
		this.colorRepository = colorRepository;
		
	}	

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		checkBrandCount(createCarRequest.getBrandId());
		checkBrandExists(createCarRequest.getBrandId());
		checkColorExists(createCarRequest.getColorId());	
		checkCarPlate(createCarRequest.getCarPlate());
		Car car = this.modelMapperService.forRequest()
					.map(createCarRequest, Car.class);
		car.setState(1);
			
		this.carRepository.save(car);
		return new SuccessResult("CAR ADDED");
		
	
		

	}


	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {
		
		int carId= deleteCarRequest.getId();
		this.carRepository.deleteById(carId);
		carRepository.deleteById(deleteCarRequest.getId());
		return new SuccessResult("CAR DELETED");
		
		
	}
	
	
	
	@Override
	public DataResult<CarResponse> getById(int id){
		Car car = carRepository.findById(id);
		CarResponse response = this.modelMapperService.forResponse()
				.map(car, CarResponse.class);
		return new SuccessDataResult<CarResponse>(response, "CAR GETTED");
	}
	


	@Override
	public DataResult<List<ListCarResponse>> getAll() {
		List<Car> cars = this.carRepository.findAll();
		List<ListCarResponse> response = cars.stream()
				.map(car -> this.modelMapperService.forResponse().map(car, ListCarResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListCarResponse>>(response, "CARS GETTED");
	}




	@Override
	public Result update(UpdateCarRequest updateCarRequest) {
		checkIfCarExists(updateCarRequest.getId());
		Car carToUpdate = modelMapperService.forRequest().map(updateCarRequest,Car.class);
		
		this.carRepository.save(carToUpdate);
		return new SuccessResult("CAR UPDATED");
		
		
	}
	private void checkBrandCount(int brandId) {
		List<Car> cars = carRepository.findByBrandId(brandId);
		if(cars.size()>= 5) {
			throw new BusinessException("CANNOT BE MORE THAN 5 CAR THE SAME BRAND");
			}
		}
	private void checkBrandExists(int brandId) {
		Brand brand = this.brandRepository.findById(brandId);
		if(brand==null) {
			throw new BusinessException("THIS BRAND COULDN'T FOUND");
			
		}
	}
	
	private void checkColorExists(int colorId) {
		Color color = this.colorRepository.findById(colorId);
		if(color==null){
			throw new BusinessException("THIS COLOR COULDN'T FOUND");
		}
	}
	
	private void checkCarPlate(String carPlate){
		Car car = this.carRepository.findByCarPlate(carPlate);
		if (car != null) {
			throw new BusinessException("THIS CAR COULDN'T FOUND");
		}
	}
	private void checkIfCarExists(int carId) {
		Car car = this.carRepository.findById(carId);
		if(car==null) {
			throw new BusinessException("THIS CAR COULDN'T EXISTS");
		}
	}
	
	
			
}
