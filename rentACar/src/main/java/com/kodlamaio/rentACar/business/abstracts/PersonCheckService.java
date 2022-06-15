package com.kodlamaio.rentACar.business.abstracts;

import java.rmi.RemoteException;

import com.kodlamaio.rentACar.business.request.users.CreateUserRequest;

public interface PersonCheckService {
	boolean CheckIfCorrectPerson(CreateUserRequest createUserRequest) throws NumberFormatException, RemoteException;
}
