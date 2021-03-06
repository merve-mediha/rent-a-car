package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.request.colors.CreateColorRequest;
import com.kodlamaio.rentACar.business.request.colors.DeleteColorRequest;
import com.kodlamaio.rentACar.business.request.colors.UpdateColorRequest;
import com.kodlamaio.rentACar.business.responses.colors.ColorResponse;
import com.kodlamaio.rentACar.business.responses.colors.ListColorResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface ColorService {
	Result add(CreateColorRequest createColorRequest);
	Result delete(DeleteColorRequest deleteColorRequest);

	Result update(UpdateColorRequest updateColorRequest);

	DataResult<List<ListColorResponse>> getAll();

	DataResult<ColorResponse> getById(int id);

}
