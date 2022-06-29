package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.request.addresses.CreateAddressRequest;
import com.kodlamaio.rentACar.business.request.addresses.DeleteAddressRequest;
import com.kodlamaio.rentACar.business.request.addresses.UpdateAddressRequest;
import com.kodlamaio.rentACar.business.responses.adresses.AddressResponse;
import com.kodlamaio.rentACar.business.responses.adresses.ListAddressResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface AddressService {
	Result addSameAddress(CreateAddressRequest createAddressRequest);
	Result addDifferentAddress(CreateAddressRequest createAddressRequest);
	Result delete(DeleteAddressRequest deleteAddressRequest);
	Result updateSameAddress(UpdateAddressRequest updateAddressRequest);
	Result updateDifferentAddress(UpdateAddressRequest updateAddressRequest);
	DataResult<AddressResponse> getById(int id);
	DataResult<List<ListAddressResponse>> getAll();
//	DataResult<List<ListAddressResponse>> getAllBillingAddress(int userId, int addressType);
//	DataResult<List<ListAddressResponse>> getAllContactAddress(int userId, int addressType);
	
	

}
