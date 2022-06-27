package com.kodlamaio.rentACar.core.utilities.adapters.abstracts;

import java.rmi.RemoteException;

import com.kodlamaio.rentACar.business.request.individualCustomers.CreateIndividualCustomerRequest;

public interface PersonCheckService {
	boolean CheckIfCorrectPerson(CreateIndividualCustomerRequest user) throws NumberFormatException, RemoteException;
}
