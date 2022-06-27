package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.AdditionalServiceItemService;
import com.kodlamaio.rentACar.business.request.additionalServiceItems.CreateAdditionalServiceItemRequest;
import com.kodlamaio.rentACar.business.request.additionalServiceItems.DeleteAdditionalServiceItemRequest;
import com.kodlamaio.rentACar.business.request.additionalServiceItems.UpdateAdditionalServiceItemRequest;
import com.kodlamaio.rentACar.business.responses.additionalServiceItems.AdditionalServiceItemResponse;
import com.kodlamaio.rentACar.business.responses.additionalServiceItems.ListAdditionalServiceItemResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/aditionalServiceItems")
public class AdditionalServiceItemsController {

	AdditionalServiceItemService additionalServiceItemService;

	public AdditionalServiceItemsController(AdditionalServiceItemService additionalServiceItemService) {

		this.additionalServiceItemService = additionalServiceItemService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateAdditionalServiceItemRequest createAdditionalItemRequest) {
		return this.additionalServiceItemService.add(createAdditionalItemRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestBody UpdateAdditionalServiceItemRequest updateAdditionalItemRequest) {
		return this.additionalServiceItemService.update(updateAdditionalItemRequest);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteAdditionalServiceItemRequest deleteAdditionalItemRequest) {
		return this.additionalServiceItemService.delete(deleteAdditionalItemRequest);
	}

	@GetMapping("/getall")
	public DataResult<List<ListAdditionalServiceItemResponse>> getAll() {
		return this.additionalServiceItemService.getAll();
	}

	@GetMapping("/getbyid")
	public DataResult<AdditionalServiceItemResponse> getById(@RequestParam int id) {
		return this.additionalServiceItemService.getById(id);
	}
}
