package com.kodlamaio.rentACar.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kodlamaio.rentACar.business.abstracts.AddressService;
import com.kodlamaio.rentACar.business.request.addresses.CreateAddressRequest;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public class AddressController {
	AddressService addressService;

	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateAddressRequest createAddressRequest) {
		
		return this.addressService.add(createAddressRequest);
	}
}
