package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.AdditionalServiceService;
import com.kodlamaio.rentACar.business.request.additionalServices.CreateAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.request.additionalServices.DeleteAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.request.additionalServices.UpdateAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.responses.additionalServices.AdditionalServiceResponse;
import com.kodlamaio.rentACar.business.responses.additionalServices.ListAdditionalServiceResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additionalServices")
public class AdditionalServicesController {
	AdditionalServiceService additionalService;
	
	public AdditionalServicesController(AdditionalServiceService additionalService) {
			this.additionalService = additionalService;
		}

	@PostMapping("/add")
	public Result add(@RequestBody CreateAdditionalServiceRequest createAdditionalRequest) {
		
		return this.additionalService.add(createAdditionalRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestBody UpdateAdditionalServiceRequest updateAdditionalRequest) {
		return this.additionalService.update(updateAdditionalRequest);
	}
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteAdditionalServiceRequest deleteAdditionalRequest) {
	return this.additionalService.delete(deleteAdditionalRequest);	
	}
	@GetMapping("/getall")
	public DataResult<List<ListAdditionalServiceResponse>> getAll(){
		return this.additionalService.getAll();
	}
	@GetMapping("/getbyid")
	public DataResult<AdditionalServiceResponse> getById(@RequestParam int id){
		return this.additionalService.getById(id);
	}
}
