package com.kodlamaio.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.OrderedAdditionalServicesService;
import com.kodlamaio.rentACar.business.request.orderedAdditionalServices.CreateOrderedAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.request.orderedAdditionalServices.DeleteOrderedAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.request.orderedAdditionalServices.UpdateOrderedAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalServices.ListOrderedAdditionalServiceResponse;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalServices.OrderedAdditionalServiceResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalServiceItemRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.OrderedAdditionalServiceRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.AdditionalServiceItem;
import com.kodlamaio.rentACar.entities.concretes.OrderedAdditionalService;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServicesService {
	
	ModelMapperService modelMapperService;
	OrderedAdditionalServiceRepository orderedAdditionalServiceRepository;
	AdditionalServiceItemRepository additionalServiceItemRepository;
	RentalRepository rentalRepository;
	
	@Autowired
	public OrderedAdditionalServiceManager(ModelMapperService modelMapperService, AdditionalServiceItemRepository additionalServiceItemRepository,
			RentalRepository rentalRepository, OrderedAdditionalServiceRepository orderedAdditionalServiceRepository) {
		this.modelMapperService = modelMapperService;
		this.orderedAdditionalServiceRepository = orderedAdditionalServiceRepository;
		this.additionalServiceItemRepository = additionalServiceItemRepository;
		this.rentalRepository = rentalRepository;
	} 
	@Override
	public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) {
		checkRentalId(createOrderedAdditionalServiceRequest.getRentalId());
		checkAdditionalServiceItem(createOrderedAdditionalServiceRequest.getAdditionalServiceItemId());
		checkDateOrderedAdditionalService(createOrderedAdditionalServiceRequest.getPickupDate(),
				createOrderedAdditionalServiceRequest.getReturnDate());
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest()
				.map(createOrderedAdditionalServiceRequest,OrderedAdditionalService.class);
		this.orderedAdditionalServiceRepository.save(orderedAdditionalService);
		orderedAdditionalService.setTotalDays(calculateTotalDays(orderedAdditionalService));
		
		double additionalServiceItemPrice = this.additionalServiceItemRepository.findById(createOrderedAdditionalServiceRequest.getAdditionalServiceItemId()).getDailyPrice();
		double totalPrice = calculateTotalPriceAdditionalService(calculateTotalDays(orderedAdditionalService), additionalServiceItemPrice);
		orderedAdditionalService.setTotalPrice(totalPrice);
		
		this.orderedAdditionalServiceRepository.save(orderedAdditionalService);
		return new SuccessResult("ADDITIONAL SERVICE ADDED");
		
	}
	@Override
	public Result update(UpdateOrderedAdditionalServiceRequest updateAdditionalServiceRequest) {
		checkRentalId(updateAdditionalServiceRequest.getRentalId());
		checkAdditionalServiceItem(updateAdditionalServiceRequest.getAdditionalServiceItemId());
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest()
				.map(updateAdditionalServiceRequest,OrderedAdditionalService.class);
		this.orderedAdditionalServiceRepository.save(orderedAdditionalService);
		orderedAdditionalService.setTotalDays(calculateTotalDays(orderedAdditionalService));
		
		double additionalServiceItemPrice = this.additionalServiceItemRepository.findById(updateAdditionalServiceRequest.
				getAdditionalServiceItemId()).getDailyPrice();
		double totalPrice = calculateTotalPriceAdditionalService(calculateTotalDays(orderedAdditionalService), additionalServiceItemPrice);
		orderedAdditionalService.setTotalPrice(totalPrice);
		
		this.orderedAdditionalServiceRepository.save(orderedAdditionalService);
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
	//////////////////////////////////////////////////////////////////////////7
	
	private void checkAdditionalServiceItem(int additionalServiceItemId) {
		AdditionalServiceItem additionalServiceItem = this.additionalServiceItemRepository.findById(additionalServiceItemId);
		if(additionalServiceItem==null) {
			throw new BusinessException("THIS ADDITIONAL ITEM IS NOT EXIST");
		}
	}
		
		private void checkRentalId(int rentalId) {
			Rental rental = this.rentalRepository.findById(rentalId);
			if (rental == null) {
				throw new BusinessException("THERE.IS.NOT.THIS.RENTAL");
			}
		}
		
	private void checkDateOrderedAdditionalService(LocalDate pickupDate, LocalDate returDate) {
		if(!pickupDate.isBefore(returDate)||pickupDate.isBefore(LocalDate.now())) {
			throw new BusinessException("PICKUPDATE AND RETURNDATE ERROR");
		}
	}	
	
	private double calculateTotalPriceAdditionalService(int days, double price) {
		return days*price;
	}
	
	private int calculateTotalDays(OrderedAdditionalService orderedAdditionalService) {
		int totalDays = (int) ChronoUnit.DAYS.between(orderedAdditionalService.getPickupDate(), orderedAdditionalService.getReturnDate());
				return totalDays;
	}
	

	

}
