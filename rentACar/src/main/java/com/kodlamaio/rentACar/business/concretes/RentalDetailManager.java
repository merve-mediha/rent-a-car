package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.RentalDetailService;
import com.kodlamaio.rentACar.business.request.rentalDetails.CreateRentalDetailRequest;
import com.kodlamaio.rentACar.business.request.rentalDetails.DeleteRentalDetailRequest;
import com.kodlamaio.rentACar.business.request.rentalDetails.UpdateRentalDetailRequest;
import com.kodlamaio.rentACar.business.responses.rentalDetails.ListRentalDetailResponse;
import com.kodlamaio.rentACar.business.responses.rentalDetails.RentalDetailResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalDetailRepository;
import com.kodlamaio.rentACar.entities.concretes.RentalDetail;

@Service
public class RentalDetailManager implements RentalDetailService {
	@Autowired
	RentalDetailRepository rentalDetailRepository;

	
	@Autowired
	ModelMapperService modelMapperService;

	@Override
	public Result add(CreateRentalDetailRequest createRentalDetailRequest) {
		RentalDetail rentalDetail=this.modelMapperService.forRequest().map(createRentalDetailRequest, RentalDetail.class);
		rentalDetailRepository.save(rentalDetail);
		return new SuccessResult("RENTAL DETAIL ADDDED TO INVOICE");
	}

	@Override
	public Result delete(DeleteRentalDetailRequest deleteRentalDetailRequest) {
		RentalDetail rentalDetail = modelMapperService.forRequest().map(deleteRentalDetailRequest, RentalDetail.class);
		rentalDetailRepository.delete(rentalDetail);
		return new SuccessResult("RENTAL DETAIL DELETED");
	}

	@Override
	public Result update(UpdateRentalDetailRequest updateRentalDetailRequest) {
		RentalDetail rentalDetailToUpdate = this.modelMapperService.forRequest().map(updateRentalDetailRequest, RentalDetail.class);
		rentalDetailRepository.save(rentalDetailToUpdate);
		
				
		return new SuccessResult("RENTAL DETAIL UPDATED TO INVOICE");
	}

	@Override
	public DataResult<List<ListRentalDetailResponse>> getAll() {
		List<RentalDetail> rentalDetails = this.rentalDetailRepository.findAll();
		List<ListRentalDetailResponse> response= rentalDetails.stream()
			.map(item->this.modelMapperService.forResponse().map(item, ListRentalDetailResponse.class))
			.collect(Collectors.toList());
		return new SuccessDataResult<List<ListRentalDetailResponse>>(response);
	}

	@Override
	public DataResult<RentalDetail> getById(RentalDetailResponse rentalDetailResponse) {
		RentalDetail rentalDetail = this.modelMapperService.forResponse().map(rentalDetailResponse,	 RentalDetail.class);
		rentalDetail = rentalDetailRepository.findById(rentalDetailResponse.getId());
		
		return new SuccessDataResult<RentalDetail>(rentalDetail);
	}
}
