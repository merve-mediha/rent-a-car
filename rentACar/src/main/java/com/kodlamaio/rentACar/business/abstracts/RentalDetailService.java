package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.request.rentalDetails.CreateRentalDetailRequest;
import com.kodlamaio.rentACar.business.request.rentalDetails.DeleteRentalDetailRequest;
import com.kodlamaio.rentACar.business.request.rentalDetails.UpdateRentalDetailRequest;
import com.kodlamaio.rentACar.business.responses.rentalDetails.ListRentalDetailResponse;
import com.kodlamaio.rentACar.business.responses.rentalDetails.RentalDetailResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.RentalDetail;

public interface RentalDetailService {
	Result add(CreateRentalDetailRequest createRentalDetailRequest);
	Result delete(DeleteRentalDetailRequest deleteRentalDetailRequest);
	Result update(UpdateRentalDetailRequest updateRentalDetailRequest);
	DataResult<List<ListRentalDetailResponse>> getAll();
	DataResult<RentalDetail> getById(RentalDetailResponse rentalDetailResponse);
}
