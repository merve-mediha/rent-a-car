package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.request.rentals.CreateRentalRequest;
import com.kodlamaio.rentACar.business.request.rentals.DeleteRentalRequest;
import com.kodlamaio.rentACar.business.request.rentals.UpdateRentalRequest;
import com.kodlamaio.rentACar.business.responses.rentals.ListRentalResponse;
import com.kodlamaio.rentACar.business.responses.rentals.RentalResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
@RestController
@RequestMapping("/api/rentals")
public class RentalsController {
	RentalService rentalService;

	public RentalsController(RentalService rentalService) {
		this.rentalService = rentalService;
	}
	
	@PostMapping("/addIndividualCustomer")
	public Result addIndividualCustomer(@RequestBody CreateRentalRequest createRentalRequest) {
		return this.rentalService.addIndividualCustomer(createRentalRequest);
	}
	
	@PostMapping("/addCorporateCustomer")
	public Result addCorporateCustomer(@RequestBody CreateRentalRequest createRentalRequest) {
		return this.rentalService.addCorporateCustomer(createRentalRequest);
	}
	
	@PostMapping("/updateIndividualCustomer")
	public Result updateIndividualCustomer(@RequestBody UpdateRentalRequest updateRentalRequest) {
		return this.rentalService.updateIndividualCustomer(updateRentalRequest);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteRentalRequest deleteRentalRequest) {
		return this.rentalService.delete(deleteRentalRequest);
	}

	@GetMapping("/getall")
	public DataResult<List<ListRentalResponse>> getAll() {
		return this.rentalService.getall();

	}

	@GetMapping("/getbyid")
	public DataResult<RentalResponse> getById(@RequestParam int id) {
		return this.rentalService.getById(id);
	}
}
