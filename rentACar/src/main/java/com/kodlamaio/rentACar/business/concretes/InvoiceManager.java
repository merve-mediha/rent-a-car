package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.InvoiceService;
import com.kodlamaio.rentACar.business.request.invoices.CreateInvoiceRequest;
import com.kodlamaio.rentACar.business.responses.invoices.InvoiceResponse;
import com.kodlamaio.rentACar.business.responses.invoices.ListInvoiceResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.InvoiceRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalDetailRepository;
import com.kodlamaio.rentACar.entities.concretes.Invoice;
import com.kodlamaio.rentACar.entities.concretes.RentalDetail;

@Service
public class InvoiceManager implements InvoiceService {
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	ModelMapperService modelMapperService;
	
	@Autowired
	RentalDetailRepository rentalDetailRepository;
	
	
	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		Invoice invoice= modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		RentalDetail rentalDetail= rentalDetailRepository.findById(createInvoiceRequest.getRentalDetailId());
		invoice.setRentalDetail(rentalDetail);
		invoiceRepository.save(invoice);
		return new SuccessResult("Fatura eklendi");
	}

	@Override
	public DataResult<List<ListInvoiceResponse>> getall() {
		List<Invoice> invoices = this.invoiceRepository.findAll();
		
		List<ListInvoiceResponse> response = invoices.stream()
				.map(invoice-> this.modelMapperService.forResponse().map(invoice,ListInvoiceResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListInvoiceResponse>>(response);
	}

	@Override
	public DataResult<InvoiceResponse> getById(int id) {
		Invoice invoice = this.invoiceRepository.findById(id);
		InvoiceResponse invoiceResponse = this.modelMapperService.forResponse().map(invoice, InvoiceResponse.class);
		return new SuccessDataResult<InvoiceResponse>(invoiceResponse);
	}

}
