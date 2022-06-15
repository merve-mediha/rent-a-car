package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AdditionalServiceItemService;
import com.kodlamaio.rentACar.business.request.additionalItems.CreateAdditionalServiceItemRequest;
import com.kodlamaio.rentACar.business.request.additionalItems.DeleteAdditionalServiceItemRequest;
import com.kodlamaio.rentACar.business.request.additionalItems.UpdateAdditionalServiceItemRequest;
import com.kodlamaio.rentACar.business.responses.additionalServiceItems.AdditionalServiceItemResponse;
import com.kodlamaio.rentACar.business.responses.additionalServiceItems.ListAdditionalServiceItemResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalServiceItemRepository;
import com.kodlamaio.rentACar.entities.concretes.AdditionalServiceItem;
@Service
public class AdditionalServiceItemManager implements AdditionalServiceItemService {
	ModelMapperService modelMapperService;
	AdditionalServiceItemRepository additionalItemRepository;
@Autowired
	public AdditionalServiceItemManager(ModelMapperService modelMapperService,
			AdditionalServiceItemRepository additionalItemRepository) {
		this.modelMapperService = modelMapperService;
		this.additionalItemRepository = additionalItemRepository;
	}

	@Override
	public Result add(CreateAdditionalServiceItemRequest createAdditionalItemRequest) {
		AdditionalServiceItem additionalItem = this.modelMapperService.forRequest().map(createAdditionalItemRequest,
				AdditionalServiceItem.class);
		this.additionalItemRepository.save(additionalItem);
		return new SuccessResult("ADDITIONAL.ITEM.ADDED");
	}

	@Override
	public Result update(UpdateAdditionalServiceItemRequest updateAdditionalItemRequest) {
		AdditionalServiceItem additionalItemToUpdate = this.modelMapperService.forRequest().map(updateAdditionalItemRequest,
				AdditionalServiceItem.class);
		this.additionalItemRepository.save(additionalItemToUpdate);
		return new SuccessResult("ADDITIONAL.ITEM.UPDATED");
	}

	@Override
	public Result delete(DeleteAdditionalServiceItemRequest deleteAdditionalItemRequest) {
		this.additionalItemRepository.deleteById(deleteAdditionalItemRequest.getId());
		return new SuccessResult("ADDITIONAL.ITEM.DELETED");
	}

	@Override
	public DataResult<List<ListAdditionalServiceItemResponse>> getAll() {
		List<AdditionalServiceItem> additionalItems = this.additionalItemRepository.findAll();
		List<ListAdditionalServiceItemResponse> response = additionalItems.stream()
				.map(additionalItem -> this.modelMapperService.forResponse().map(additionalItem,
						ListAdditionalServiceItemResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListAdditionalServiceItemResponse>>(response);
	}

	@Override
	public DataResult<AdditionalServiceItemResponse> getById(int id) {
		AdditionalServiceItem additionalItem = this.additionalItemRepository.findById(id);
		AdditionalServiceItemResponse response = this.modelMapperService.forResponse().map(additionalItem,
				AdditionalServiceItemResponse.class);
		return new SuccessDataResult<AdditionalServiceItemResponse>(response);
	}
}