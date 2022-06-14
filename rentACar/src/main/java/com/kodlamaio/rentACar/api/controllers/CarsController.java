package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.CarService;
import com.kodlamaio.rentACar.business.request.cars.CreateCarRequest;
import com.kodlamaio.rentACar.business.request.cars.DeleteCarRequest;
import com.kodlamaio.rentACar.business.request.cars.UpdateCarRequest;
import com.kodlamaio.rentACar.business.responses.cars.CarResponse;
import com.kodlamaio.rentACar.business.responses.cars.ListCarResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
@RestController
@RequestMapping("/api/cars")

public class CarsController {
	
	private CarService carService;

	public CarsController(CarService carService) {
		this.carService = carService;
	}
	@PostMapping("/add")
	public Result add(@RequestBody CreateCarRequest createCarRequest) {
		return this.carService.add(createCarRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateCarRequest updatecarRequest) {
		return this.carService.update(updatecarRequest);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteCarRequest deleteCarRequest) {
		return this.carService.delete(deleteCarRequest);
	}
	
//	@GetMapping("/getbyid)
//	public DataResult<Car> getById(@RequestBody CarResponse carResponse) {
//		return carService.getById(carResponse);
//	}
	
	@GetMapping("/getbyid")
	public DataResult<CarResponse> getById(@RequestParam int id) {
		return carService.getById(id);
	}
	
	
	@GetMapping("/getAll")
	public DataResult<List<ListCarResponse>> getAll() {
		return carService.getAll();
	}
	
}
