package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.RentalDetailService;
import com.kodlamaio.rentACar.business.request.rentalDetails.CreateRentalDetailRequest;
import com.kodlamaio.rentACar.business.request.rentalDetails.DeleteRentalDetailRequest;
import com.kodlamaio.rentACar.business.request.rentalDetails.UpdateRentalDetailRequest;
import com.kodlamaio.rentACar.business.responses.rentalDetails.ListRentalDetailResponse;
import com.kodlamaio.rentACar.business.responses.rentalDetails.RentalDetailResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rentaldetails")
public class RentalDetailsController {
	@Autowired
	RentalDetailService rentalDetailService;

	public RentalDetailsController(RentalDetailService rentalDetailService) {
		this.rentalDetailService = rentalDetailService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateRentalDetailRequest createRentalDetailRequest) {
		return this.rentalDetailService.add(createRentalDetailRequest);
	}
		
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteRentalDetailRequest deleteRentalDetailRequest) {
		return this.rentalDetailService.delete(deleteRentalDetailRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateRentalDetailRequest updateRentalDetailRequest) {
		return this.update(updateRentalDetailRequest);
				}
	@GetMapping("/getall")
	public DataResult<List<ListRentalDetailResponse>> getAll(){
		return this.rentalDetailService.getAll();
		
	}
	
	public DataResult<RentalDetailResponse> getById(@RequestParam int id){
		return this.rentalDetailService.getById(id);
	}
	
	

}
