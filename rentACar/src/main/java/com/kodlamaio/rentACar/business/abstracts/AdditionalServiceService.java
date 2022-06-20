package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.request.additionalServices.CreateAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.request.additionalServices.DeleteAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.request.additionalServices.UpdateAdditionalServiceRequest;
import com.kodlamaio.rentACar.business.responses.additionalServices.AdditionalServiceResponse;
import com.kodlamaio.rentACar.business.responses.additionalServices.ListAdditionalServiceResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface AdditionalServiceService {
	Result add(CreateAdditionalServiceRequest createAdditionalRequest);

	Result update(UpdateAdditionalServiceRequest updateAdditionalRequest);

	Result delete(DeleteAdditionalServiceRequest deleteAdditionalRequest);

	DataResult<List<ListAdditionalServiceResponse>> getAll();

	DataResult<AdditionalServiceResponse> getById(int id);
}