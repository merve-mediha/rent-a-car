package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.request.invoices.CreateInvoiceRequest;
import com.kodlamaio.rentACar.business.responses.invoices.InvoiceResponse;
import com.kodlamaio.rentACar.business.responses.invoices.ListInvoiceResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface InvoiceService {
	Result add(CreateInvoiceRequest createInvoiceRequest);

	

	DataResult<List<ListInvoiceResponse>> getall();

	DataResult<InvoiceResponse> getById(int id);

}
