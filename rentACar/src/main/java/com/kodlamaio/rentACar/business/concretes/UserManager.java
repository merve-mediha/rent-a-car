package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.UserService;
import com.kodlamaio.rentACar.business.request.users.CreateUserRequest;
import com.kodlamaio.rentACar.business.request.users.DeleteUserRequest;
import com.kodlamaio.rentACar.business.request.users.UpdateUserRequest;
import com.kodlamaio.rentACar.business.responses.users.ListUserResponse;
import com.kodlamaio.rentACar.business.responses.users.UserResponse;
import com.kodlamaio.rentACar.core.utilities.adapters.abstracts.PersonCheckService;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.UserRepository;
import com.kodlamaio.rentACar.entities.concretes.User;
@Service
public class UserManager implements UserService{
	UserRepository userRepository;
	ModelMapperService modelMapperService;
	PersonCheckService personCheckService;
	
	
	
	@Autowired
	public UserManager(UserRepository userRepository, ModelMapperService modelMapperService,
			PersonCheckService personCheckService) {
		this.userRepository = userRepository;
		this.modelMapperService = modelMapperService;
		this.personCheckService = personCheckService;
	}

	@Override
	public Result add(CreateUserRequest createUserRequest) {
		User user = this.modelMapperService.forRequest().map(createUserRequest, User.class );
		this.userRepository.save(user);
			return new SuccessResult("USER ADDED");
		
	}

	@Override
	public Result update(UpdateUserRequest updateUserRequest) {
		User userToUpdate = this.modelMapperService.forRequest().map(updateUserRequest, User.class );
		this.userRepository.save(userToUpdate);
		return new SuccessResult("USER UPDATED");
	}

	@Override
	public Result delete(DeleteUserRequest deleteUserRequest) {
		this.userRepository.deleteById(deleteUserRequest.getId());
		return new SuccessResult("USER DELETED");
	}

	@Override
	public DataResult<List<ListUserResponse>> getAll() {
		List<User> users= this.userRepository.findAll();
		List<ListUserResponse> response = users.stream().map(user-> this.modelMapperService.forResponse()
				.map(user, ListUserResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListUserResponse>> (response);
	}

	@Override
	public DataResult<UserResponse> getById(int id) {
		User user = userRepository.findById(id);
		UserResponse response = this.modelMapperService.forResponse().map(user, UserResponse.class);
		return new SuccessDataResult<UserResponse>(response);
		
	}

	@Override
	public DataResult<List<ListUserResponse>> getAll(Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
		List<User> users= this.userRepository.findAll(pageable).getContent();
		List<ListUserResponse> response = users.stream().map(user-> this.modelMapperService.forResponse()
				.map(user, ListUserResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListUserResponse>> (response);
	}

	

	
	
	
}
