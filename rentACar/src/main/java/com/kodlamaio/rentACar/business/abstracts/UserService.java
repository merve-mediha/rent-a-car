package com.kodlamaio.rentACar.business.abstracts;

import java.rmi.RemoteException;
import java.util.List;

import com.kodlamaio.rentACar.business.request.users.CreateUserRequest;
import com.kodlamaio.rentACar.business.request.users.DeleteUserRequest;
import com.kodlamaio.rentACar.business.request.users.UpdateUserRequest;
import com.kodlamaio.rentACar.business.responses.users.ListUserResponse;
import com.kodlamaio.rentACar.business.responses.users.UserResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface UserService {
	Result add(CreateUserRequest createUserRequest) throws NumberFormatException, RemoteException;
	Result update(UpdateUserRequest updateUserRequest);
	Result delete(DeleteUserRequest deleteUserRequest);
	DataResult<List<ListUserResponse>> getAll();
	DataResult<UserResponse> getById(int id);
	DataResult<List<ListUserResponse>> getAll(int pageNumber, int pageSize);

}
