package com.kodlamaio.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.InvoiceService;
import com.kodlamaio.rentACar.business.request.invoices.CreateInvoiceRequest;
import com.kodlamaio.rentACar.business.responses.invoices.InvoiceResponse;
import com.kodlamaio.rentACar.business.responses.invoices.ListInvoiceResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {
	InvoiceService invoiceService;

	public InvoicesController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateInvoiceRequest createInvoiceRequest) {
		return this.invoiceService.add(createInvoiceRequest);
		
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListInvoiceResponse>> getAll(){
		return this.invoiceService.getall();
	}
	
	@GetMapping("/getbyid")
	public DataResult<InvoiceResponse> getById(@RequestParam int id){
		return this.invoiceService.getById(id);
	}
	
	
	

}
