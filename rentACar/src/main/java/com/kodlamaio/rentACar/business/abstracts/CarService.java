package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.request.cars.CreateCarRequest;
import com.kodlamaio.rentACar.business.request.cars.DeleteCarRequest;
import com.kodlamaio.rentACar.business.request.cars.UpdateCarRequest;
import com.kodlamaio.rentACar.business.responses.cars.CarResponse;
import com.kodlamaio.rentACar.business.responses.cars.ListCarResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface CarService {
	Result add(CreateCarRequest createCarRequest);
	Result delete(DeleteCarRequest deleteCarRequest);
	Result update(UpdateCarRequest updateCarRequest);
	DataResult<CarResponse> getById(int id);
	DataResult<List<ListCarResponse>> getAll();

}
