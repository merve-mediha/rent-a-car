package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.AdditionalServiceItemService;
import com.kodlamaio.rentACar.business.request.additionalItems.CreateAdditionalServiceItemRequest;
import com.kodlamaio.rentACar.business.request.additionalItems.DeleteAdditionalServiceItemRequest;
import com.kodlamaio.rentACar.business.request.additionalItems.UpdateAdditionalServiceItemRequest;
import com.kodlamaio.rentACar.business.responses.additionalServiceItems.AdditionalServiceItemResponse;
import com.kodlamaio.rentACar.business.responses.additionalServiceItems.ListAdditionalServiceItemResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/aditionalServiceItems")
public class AdditionalServiceItemsController {

	AdditionalServiceItemService additionalItemService;
	
public AdditionalServiceItemsController(AdditionalServiceItemService additionalItemService) {
	
		this.additionalItemService = additionalItemService;
	}
@PostMapping("/add")
public Result add(@RequestBody CreateAdditionalServiceItemRequest createAdditionalItemRequest) {
	 return this.additionalItemService.add(createAdditionalItemRequest);
}
@PostMapping("/update")
public Result update(@RequestBody UpdateAdditionalServiceItemRequest updateAdditionalItemRequest) {
	return this.additionalItemService.update(updateAdditionalItemRequest);
}
@PostMapping("/delete")
public Result delete(@RequestBody DeleteAdditionalServiceItemRequest deleteAdditionalItemRequest) {
	return this.additionalItemService.delete(deleteAdditionalItemRequest);
}
@GetMapping("/getall")
public DataResult<List<ListAdditionalServiceItemResponse>> getAll(){
	return this.additionalItemService.getAll();
}
@GetMapping("/getbyid")
public DataResult<AdditionalServiceItemResponse> getById(@RequestParam int id){
	return this.additionalItemService.getById(id);
}
}
