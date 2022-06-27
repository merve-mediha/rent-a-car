package com.kodlamaio.rentACar.api.controllers;

import java.rmi.RemoteException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.UserService;
import com.kodlamaio.rentACar.business.request.individualCustomers.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.request.individualCustomers.DeleteIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.request.individualCustomers.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.request.users.CreateUserRequest;
import com.kodlamaio.rentACar.business.request.users.DeleteUserRequest;
import com.kodlamaio.rentACar.business.request.users.UpdateUserRequest;
import com.kodlamaio.rentACar.business.responses.users.ListUserResponse;
import com.kodlamaio.rentACar.business.responses.users.UserResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/users")
public class UsersController {
	UserService UserService;

	public UsersController(UserService UserService) {
		this.UserService = UserService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest) throws NumberFormatException, RemoteException {
		return this.UserService.add(createIndividualCustomerRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		return this.UserService.update(updateIndividualCustomerRequest);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteIndividualCustomerRequest deleteUserRequest) {
		return this.UserService.delete(deleteUserRequest);
	}

	

	@GetMapping("/getbyid")
	public DataResult<UserResponse> getById(@RequestParam @Valid int id) {
		return this.UserService.getById(id);
	}
	@GetMapping("/getallbypage")
	DataResult<List<ListUserResponse>> getAll(@RequestParam int pageNumber, int pageSize){
		return this.UserService.getAll(pageNumber,pageSize);
	}
	
	@GetMapping("/getall")
	DataResult<List<ListUserResponse>> getAll(){
		return this.UserService.getAll();
		
	}
	

}
