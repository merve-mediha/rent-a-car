package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.request.additionalServiceItems.CreateAdditionalServiceItemRequest;
import com.kodlamaio.rentACar.business.request.additionalServiceItems.DeleteAdditionalServiceItemRequest;
import com.kodlamaio.rentACar.business.request.additionalServiceItems.UpdateAdditionalServiceItemRequest;
import com.kodlamaio.rentACar.business.responses.additionalServiceItems.AdditionalServiceItemResponse;
import com.kodlamaio.rentACar.business.responses.additionalServiceItems.ListAdditionalServiceItemResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface AdditionalServiceItemService {
	Result add(CreateAdditionalServiceItemRequest createAdditionalServiceItemRequest);

	Result update(UpdateAdditionalServiceItemRequest updateAdditionalServiceItemRequest);

	Result delete(DeleteAdditionalServiceItemRequest deleteAdditionalServiceItemReques);

	DataResult<List<ListAdditionalServiceItemResponse>> getAll();

	DataResult<AdditionalServiceItemResponse> getById(int id);
}