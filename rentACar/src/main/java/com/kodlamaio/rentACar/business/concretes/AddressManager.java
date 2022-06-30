package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AddressService;
import com.kodlamaio.rentACar.business.request.addresses.CreateAddressRequest;
import com.kodlamaio.rentACar.business.request.addresses.DeleteAddressRequest;
import com.kodlamaio.rentACar.business.request.addresses.UpdateAddressRequest;
import com.kodlamaio.rentACar.business.responses.addresses.AddressResponse;
import com.kodlamaio.rentACar.business.responses.addresses.ListAddressResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AddressRepository;
import com.kodlamaio.rentACar.entities.concretes.Address;
@Service
public class AddressManager implements AddressService{
	AddressRepository addressRepository;
	ModelMapperService modelMapperService;
	
	@Autowired
	public AddressManager(AddressRepository addressRepository, ModelMapperService modelMapperService) {
		this.addressRepository = addressRepository;
		this.modelMapperService = modelMapperService;
	}


	@Override
	public Result addSameAddress(CreateAddressRequest createAddressRequest) {
		Address address = this.modelMapperService.forRequest().map(createAddressRequest, Address.class);
		address.setBillingAddress(createAddressRequest.getContactAddress());
		this.addressRepository.save(address);
		return new SuccessResult("ADDED ADDRESS");
	}


	@Override
	public Result addDifferentAddress(CreateAddressRequest createAddressRequest) {
		Address address= this.modelMapperService.forRequest().map(createAddressRequest, Address.class);
		this.addressRepository.save(address);
		return new SuccessResult("ADDRESS ADDED");
	}


	@Override
	public Result delete(DeleteAddressRequest deleteAddressRequest) {
		this.addressRepository.deleteById(deleteAddressRequest.getId());
		return new SuccessResult("ADDRESS DELETED");
	}


	@Override
	public Result updateSameAddress(UpdateAddressRequest updateAddressRequest) {
		checkIfAddressExistsById(updateAddressRequest.getId());
		Address address = this.modelMapperService.forRequest().map(updateAddressRequest, Address.class);
		address.setBillingAddress(updateAddressRequest.getContactAddress());
		this.addressRepository.save(address);
		return new SuccessResult("ADDRESS UPDATED");
	}


	@Override
	public Result updateDifferentAddress(UpdateAddressRequest updateAddressRequest) {
		checkIfAddressExistsById(updateAddressRequest.getId());
		Address address = this.modelMapperService.forRequest().map(updateAddressRequest, Address.class);
		System.out.println(updateAddressRequest.getBillingAddress()+updateAddressRequest.getContactAddress());
		this.addressRepository.save(address);
		return new SuccessResult("ADDRESS UPDATED");
	}


	@Override
	public DataResult<AddressResponse> getById(int id) {
		Address address = this.addressRepository.findById(id);
		AddressResponse addressResponse = this.modelMapperService.forResponse().map(address, AddressResponse.class);
		
		return new SuccessDataResult<AddressResponse>(addressResponse,"ADRESS RECEIVED" );
	}


	@Override
	public DataResult<List<ListAddressResponse>> getAll() {
		List<Address> listAddressResponse =this.addressRepository.findAll();
		
		List<ListAddressResponse> response = listAddressResponse.stream()
				.map(address-> this.modelMapperService.forResponse().map(address, ListAddressResponse.class))
				.collect(Collectors.toList());
	return new SuccessDataResult<List<ListAddressResponse>>(response);
	}



	private Address checkIfAddressExistsById(int id) {
		Address currentAddress;
		try {
			currentAddress =this.addressRepository.findById(id);
			
		}catch (Exception e) {
			throw new BusinessException("ADDRESS COULD NOT FOUND");
		}
		
		return currentAddress;
		
	}


}
