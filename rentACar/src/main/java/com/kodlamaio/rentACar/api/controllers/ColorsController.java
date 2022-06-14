package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.ColorService;
import com.kodlamaio.rentACar.business.request.colors.CreateColorRequest;
import com.kodlamaio.rentACar.business.request.colors.DeleteColorRequest;
import com.kodlamaio.rentACar.business.request.colors.UpdateColorRequest;
import com.kodlamaio.rentACar.business.responses.colors.ColorResponse;
import com.kodlamaio.rentACar.business.responses.colors.ListColorResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
@RestController
@RequestMapping("/api/colors")
public class ColorsController {
	@Autowired
	private ColorService colorService;
	
	
		public ColorsController(ColorService colorService) {
		this.colorService = colorService;
	}


		@GetMapping("/saycolor")   //çıkış noktası
		public String sayColor(){
			return "Hello Color";
		}
		
		@PostMapping("/add")
		public Result add(@RequestBody CreateColorRequest createColorRequest) {
			return this.colorService.add(createColorRequest);
		}
		
		@PostMapping("/delete")
		public Result delete(DeleteColorRequest deleteColorRequest) {
			return this.colorService.delete(deleteColorRequest);
		}
		
		@PostMapping("/update")
		public Result update(@RequestBody UpdateColorRequest updateColorRequest) {
			return this.colorService.update(updateColorRequest);
		}
		
		@GetMapping("/getall")
		public DataResult<List<ListColorResponse>> GetAll() {
			return this.colorService.getAll();
		}
		
		@GetMapping("/getbyid")
		private DataResult<ColorResponse> getById(int id) {
			return colorService.getById(id);
		}
	}




