package com.kodlamaio.rentACar.business.concretes;

import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.IndividualCustomerService;
import com.kodlamaio.rentACar.business.request.individualCustomers.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.request.individualCustomers.DeleteIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.request.individualCustomers.UpdateIndividualCustomerRequest;
import com.kodlamaio.rentACar.business.responses.individualCustomers.IndividualCustomerResponse;
import com.kodlamaio.rentACar.business.responses.individualCustomers.ListIndividualCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.adapters.abstracts.PersonCheckService;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.IndividualCustomerRepository;
import com.kodlamaio.rentACar.entities.concretes.IndividualCustomer;
@Service
public class IndividualCustomerManager implements IndividualCustomerService{
	ModelMapperService modelMapperService;
	IndividualCustomerRepository individualCustomerRepository;
	PersonCheckService personCheckService;
	

	

	public IndividualCustomerManager(ModelMapperService modelMapperService,
			IndividualCustomerRepository individualCustomerRepository, PersonCheckService personCheckService) {
		this.modelMapperService = modelMapperService;
		this.individualCustomerRepository = individualCustomerRepository;
		this.personCheckService = personCheckService;
	}



	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws NumberFormatException, RemoteException {
			checkCustomerNationalityFromRepository(createIndividualCustomerRequest.getIdentityNumber());
			checkIfCorrectPersonByNationalityFromMernis(createIndividualCustomerRequest);
			checkUserEmail(createIndividualCustomerRequest.getEmail());
			IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest,
					IndividualCustomer.class);
			this.individualCustomerRepository.save(individualCustomer);
			return new SuccessResult("CUSTOMER ADDED");
			
				}
	
	

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		checkCustomerNationalityFromRepository(updateIndividualCustomerRequest.getIdentityNumber());
		checkIfUserExists(updateIndividualCustomerRequest.getIndividualCustomerId());
		checkUserUpdateEmail(updateIndividualCustomerRequest.getIndividualCustomerId(), updateIndividualCustomerRequest.getEmail());
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest,
				IndividualCustomer.class);
		this.individualCustomerRepository.save(individualCustomer);
		return new SuccessResult("CUSTOMER UPDATED");
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		checkIfUserExists(deleteIndividualCustomerRequest.getIndividualCustomerId());
		this.individualCustomerRepository.deleteById(deleteIndividualCustomerRequest.getIndividualCustomerId());
		return new SuccessResult("CUSTOMER DELETED");
	}

	@Override
	public DataResult<IndividualCustomerResponse> getById(int id) {
		checkIfUserExists(id);
		IndividualCustomer individualCustomer= this.individualCustomerRepository.findById(id).get();
		IndividualCustomerResponse response = this.modelMapperService.forResponse().map(individualCustomer, IndividualCustomerResponse.class);
		return new SuccessDataResult<IndividualCustomerResponse> (response);
	}

	@Override
	public DataResult<List<ListIndividualCustomerResponse>> getAll() {
		List<IndividualCustomer> individualCustomers = this.individualCustomerRepository.findAll();
		
		List<ListIndividualCustomerResponse> response = individualCustomers.stream()
				.map(user-> this.modelMapperService.forResponse().map(individualCustomers, ListIndividualCustomerResponse.class))
				.collect(Collectors.toList());
		return null;
	}
	
	private void checkIfCorrectPersonByNationalityFromMernis(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws NumberFormatException, RemoteException{
		if (!personCheckService.CheckIfCorrectPerson(createIndividualCustomerRequest)) {
			throw new BusinessException("CUSTOMER DOES NOT EXIST IN MERNIS");
		}
	}
	private void checkCustomerNationalityFromRepository(String identityNumber) {
		IndividualCustomer customer = this.individualCustomerRepository.findByIdentityNumber(identityNumber);
		if(customer!=null) {
		throw new BusinessException("CUSTOMER EXISTS IN REPOSITORY");
		}
	}
	
	private void checkUserEmail(String email) {
		IndividualCustomer user = this.individualCustomerRepository.findByEmail(email);
		if (user != null) {
			throw new BusinessException("THIS EMAIL ALREADY EXISTS");
		}
	}
	
	private void checkIfUserExists(int id) {
		IndividualCustomer individualCustomer = this.individualCustomerRepository.findById(id).get();
		if(individualCustomer==null) {
			throw new BusinessException("THIS USER DOES NOT EXIST");
		}
	}
	
	private void checkUserUpdateEmail(int userId, String email) {
		IndividualCustomer individualCustomer = this.individualCustomerRepository.findById(userId).get();
		
		if (individualCustomer.getEmail() != email) {
			checkUserEmail(email);
		}
	}



	@Override
	public DataResult<List<ListIndividualCustomerResponse>> getAll(Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
		List<IndividualCustomer> individualCustomers = this.individualCustomerRepository.findAll(pageable).getContent();
		
	List<ListIndividualCustomerResponse> response = individualCustomers.stream()
			.map(user-> this.modelMapperService.forResponse().map(individualCustomers, ListIndividualCustomerResponse.class))
			.collect(Collectors.toList());
		return new SuccessDataResult<List<ListIndividualCustomerResponse>>(response);

	}
	

}
