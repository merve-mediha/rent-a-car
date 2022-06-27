package com.kodlamaio.rentACar.business.concretes;

import java.util.List;

import com.kodlamaio.rentACar.business.abstracts.IndividualCustomerService;
import com.kodlamaio.rentACar.business.request.individualCustomers.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.request.individualCustomers.DeleteIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.request.individualCustomers.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.responses.individualCustomers.IndividualCustomerResponse;
import com.kodlamaio.rentACar.business.responses.individualCustomers.ListIndividualCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public class IndividualCustomerManager implements IndividualCustomerService{
	

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<IndividualCustomerResponse> getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<List<ListIndividualCustomerResponse>> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
