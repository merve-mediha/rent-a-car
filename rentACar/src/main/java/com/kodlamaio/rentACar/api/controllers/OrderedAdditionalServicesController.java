package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.OrderedAdditionalServicesService;
import com.kodlamaio.rentACar.business.request.orderedAdditionalServices.CreateOrderedAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.request.orderedAdditionalServices.DeleteOrderedAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.request.orderedAdditionalServices.UpdateOrderedAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalServices.ListOrderedAdditionalServiceResponse;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalServices.OrderedAdditionalServiceResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/orderedAdditionalServices")
public class OrderedAdditionalServicesController {
	@Autowired
	OrderedAdditionalServicesService orderedAdditionalServicesService;
	
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) {
		
		return this.orderedAdditionalServicesService.add(createOrderedAdditionalServiceRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestBody UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) {
		return this.orderedAdditionalServicesService.update(updateOrderedAdditionalServiceRequest);
	}
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) {
	return this.orderedAdditionalServicesService.delete(deleteOrderedAdditionalServiceRequest);	
	}
	@GetMapping("/getall")
	public DataResult<List<ListOrderedAdditionalServiceResponse>> getAll(){
		return this.orderedAdditionalServicesService.getAll();
	}
	@GetMapping("/getbyid")
	public DataResult<OrderedAdditionalServiceResponse> getById(@RequestParam int id){
		return this.orderedAdditionalServicesService.getById(id);
	}
}
