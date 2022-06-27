package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.OrderedAdditionalServicesService;
import com.kodlamaio.rentACar.business.request.orderedAdditionalServices.CreateOrderedAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.request.orderedAdditionalServices.DeleteOrderedAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.request.orderedAdditionalServices.UpdateOrderedAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalServices.ListOrderedAdditionalServiceResponse;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalServices.OrderedAdditionalServiceResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.OrderedAdditionalServiceRepository;
import com.kodlamaio.rentACar.entities.concretes.OrderedAdditionalService;

@Service
public class OrderedAdditionalServiceItemManager implements OrderedAdditionalServicesService {
	
	ModelMapperService modelMapperService;
	OrderedAdditionalServiceRepository orderedAdditionalServiceRepository;
	
	public OrderedAdditionalServiceItemManager(ModelMapperService modelMapperService,
			OrderedAdditionalServiceRepository orderedAdditionalServiceRepository) {
		this.modelMapperService = modelMapperService;
		this.orderedAdditionalServiceRepository = orderedAdditionalServiceRepository;
	} 
	@Override
	public Result add(CreateOrderedAdditionalServiceRequest createAdditionalServiceRequest) {
		OrderedAdditionalService orderedAdditionalServiceItem = this.modelMapperService.forRequest().map(createAdditionalServiceRequest,OrderedAdditionalService.class);
		this.orderedAdditionalServiceRepository.save(orderedAdditionalServiceItem);
		
		return new SuccessResult("ADDITIONAL SERVICE ADDED");
		
	}
	@Override
	public Result update(UpdateOrderedAdditionalServiceRequest updateAdditionalServiceRequest) {
		OrderedAdditionalService orderedAdditionalServiceItemToUpdate = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest,OrderedAdditionalService.class);
		this.orderedAdditionalServiceRepository.save(orderedAdditionalServiceItemToUpdate);
		
		return new SuccessResult("ADDITIONAL SERVICE UPDATED");
	}
	@Override
	public Result delete(DeleteOrderedAdditionalServiceRequest deleteAdditionalServiceRequest) {
		this.orderedAdditionalServiceRepository.deleteById(deleteAdditionalServiceRequest.getId());
		return new SuccessResult("ADDITIONAL SERVICE DELETED");
		
	}
	@Override
	public DataResult<List<ListOrderedAdditionalServiceResponse>> getAll() {
		List<OrderedAdditionalService> orderedAdditionalServiceItem = this.orderedAdditionalServiceRepository.findAll();
		
		List<ListOrderedAdditionalServiceResponse> response  = orderedAdditionalServiceItem.stream().map(aorderedAdditionalServiceItems->this.modelMapperService.forResponse()
				.map(orderedAdditionalServiceItem,ListOrderedAdditionalServiceResponse.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListOrderedAdditionalServiceResponse>>(response);
	}
	@Override
	public DataResult<OrderedAdditionalServiceResponse> getById(int id) {
		OrderedAdditionalService orderedAdditionalServiceItem  =  orderedAdditionalServiceRepository.findById(id);
		OrderedAdditionalServiceResponse response = this.modelMapperService.forResponse().map(orderedAdditionalServiceItem, OrderedAdditionalServiceResponse.class);
		return new SuccessDataResult<OrderedAdditionalServiceResponse>(response,"ADDITIONALSERVICE GETTED"); 
	}
	
	

	

}
