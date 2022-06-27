package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.request.orderedAdditionalServices.CreateOrderedAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.request.orderedAdditionalServices.DeleteOrderedAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.request.orderedAdditionalServices.UpdateOrderedAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalServices.ListOrderedAdditionalServiceResponse;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalServices.OrderedAdditionalServiceResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface OrderedAdditionalServicesService {
	Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest);

	Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest);

	Result delete(DeleteOrderedAdditionalServiceRequest dupdateOrderedAdditionalServiceRequest);

	DataResult<List<ListOrderedAdditionalServiceResponse>> getAll();

	DataResult<OrderedAdditionalServiceResponse> getById(int id);
}