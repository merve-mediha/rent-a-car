package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AdditionalServiceService;
import com.kodlamaio.rentACar.business.request.additionals.CreateAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.request.additionals.DeleteAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.request.additionals.UpdateAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.responses.additionalServices.AdditionalServiceResponse;
import com.kodlamaio.rentACar.business.responses.additionalServices.ListAdditionalServiceResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalServiceRepository;
import com.kodlamaio.rentACar.entities.concretes.AdditionalService;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {
	@Autowired
	ModelMapperService modelMapperService;
	@Autowired
	AdditionalServiceRepository additionalServiceRepository;
	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest,AdditionalService.class);
		this.additionalServiceRepository.save(additionalService);
		
		return new SuccessResult("ADDITIONAL SERVICE ADDED");
		
	}
	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		AdditionalService additionalServiceToUpdate = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest,AdditionalService.class);
		this.additionalServiceRepository.save(additionalServiceToUpdate);
		
		return new SuccessResult("ADDITIONAL SERVICE UPDATED");
	}
	@Override
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
		this.additionalServiceRepository.deleteById(deleteAdditionalServiceRequest.getId());
		return new SuccessResult("ADDITIONAL SERVICE DELETED");
		
	}
	@Override
	public DataResult<List<ListAdditionalServiceResponse>> getAll() {
		List<AdditionalService> additionalServices = this.additionalServiceRepository.findAll();
		
		List<ListAdditionalServiceResponse> response  = additionalServices.stream().map(additionalService->this.modelMapperService.forResponse()
				.map(additionalService,ListAdditionalServiceResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListAdditionalServiceResponse>>(response);
	}
	@Override
	public DataResult<AdditionalServiceResponse> getById(int id) {
		AdditionalService additionalService  =  additionalServiceRepository.findById(id);
		AdditionalServiceResponse response = this.modelMapperService.forResponse().map(additionalService, AdditionalServiceResponse.class);
		return new SuccessDataResult<AdditionalServiceResponse>(response,"ADDITIONALSERVICE GETTED"); 
	}
	
	

	

}
