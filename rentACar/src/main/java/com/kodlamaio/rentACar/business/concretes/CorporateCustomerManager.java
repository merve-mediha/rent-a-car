package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.CorporateCustomerService;
import com.kodlamaio.rentACar.business.request.corporateCustomers.CreateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.request.corporateCustomers.DeleteCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.request.corporateCustomers.UpdateCorporateCustomerRequest;
import com.kodlamaio.rentACar.business.responses.corporateCustomers.CorporateCustomerResponse;
import com.kodlamaio.rentACar.business.responses.corporateCustomers.ListCorporateCustomerResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.CorporateCustomerRepository;
import com.kodlamaio.rentACar.entities.concretes.CorporateCustomer;
@Service
public class CorporateCustomerManager implements CorporateCustomerService {
	ModelMapperService modelMapperService;
	CorporateCustomerRepository corporateCustomerRepository;
	
	@Autowired
	public CorporateCustomerManager(ModelMapperService modelMapperService,
			CorporateCustomerRepository corporateCustomerRepository) {
		this.modelMapperService = modelMapperService;
		this.corporateCustomerRepository = corporateCustomerRepository;
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {
		checkCorporateCustomerExistsTaxNumber(createCorporateCustomerRequest.getTaxNumber());
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
		this.corporateCustomerRepository.save(corporateCustomer);
		return new SuccessResult("CORPORATE CUSTOMER ADDED");
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		checkCorporateExists(updateCorporateCustomerRequest.getCorporateCutomerId());
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);
		this.corporateCustomerRepository.save(corporateCustomer);
		return new SuccessResult("CORPORATE CUSTOMER UPDATED");
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {
		 checkCorporateExists(deleteCorporateCustomerRequest.getCorporateCutomerId());
		 this.corporateCustomerRepository.deleteById(deleteCorporateCustomerRequest.getCorporateCutomerId());
		return new SuccessResult("CORPORATE CUSTOMER DELETED");
	}

	@Override
	public DataResult<CorporateCustomerResponse> getById(int id) {
		checkCorporateExists(id);
		CorporateCustomer corporateCustomer = this.corporateCustomerRepository.findById(id);
		CorporateCustomerResponse response = this.modelMapperService.forResponse().map(corporateCustomer, CorporateCustomerResponse.class);
		
		return new SuccessDataResult<CorporateCustomerResponse>(response, "CORPORATE GETTED");
		
	}

	@Override
	public DataResult<List<ListCorporateCustomerResponse>> getAll() {
		List<CorporateCustomer> corporateCustomers = this.corporateCustomerRepository.findAll();
		List<ListCorporateCustomerResponse> response = corporateCustomers.stream().map(corporateCustomer->this.modelMapperService.forResponse()
				.map(corporateCustomer, ListCorporateCustomerResponse.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCorporateCustomerResponse>>(response,"CORPORATES LISTED");
	}

	@Override
	public DataResult<List<ListCorporateCustomerResponse>> getAll(Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		List<CorporateCustomer> customers = this.corporateCustomerRepository.findAll(pageable).getContent();
		
		
		List<ListCorporateCustomerResponse> response = customers.stream().map(customer -> this.modelMapperService.forResponse().map(customer, ListCorporateCustomerResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListCorporateCustomerResponse>>(response);
	}
	
	private void checkCorporateCustomerExistsTaxNumber(String TaxNumber) {
		CorporateCustomer corporateCustomer = this.corporateCustomerRepository.findByTaxNumber(TaxNumber);
		if(corporateCustomer != null) {
			throw new BusinessException("CORPORATE ALREADY ADDED");
		}
	}
		
	private void checkCorporateExists(int id) {
		CorporateCustomer corporateCustomer = this.corporateCustomerRepository.findById(id);
		if(corporateCustomer == null) {
			throw new BusinessException("CORPORATE HAS NOT FOUND");
		}
	}	
		
	}
	
	
	


