package com.kodlamaio.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.InvoiceService;
import com.kodlamaio.rentACar.business.request.invoices.CreateInvoiceRequest;
import com.kodlamaio.rentACar.business.request.invoices.DeleteInvoiceRequest;
import com.kodlamaio.rentACar.business.request.invoices.UpdateInvoiceRequest;
import com.kodlamaio.rentACar.business.responses.invoices.InvoiceResponse;
import com.kodlamaio.rentACar.business.responses.invoices.ListInvoiceResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalServiceItemRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.InvoiceRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.OrderedAdditionalServiceRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.AdditionalServiceItem;
import com.kodlamaio.rentACar.entities.concretes.Invoice;
import com.kodlamaio.rentACar.entities.concretes.OrderedAdditionalService;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class InvoiceManager implements InvoiceService {
	
	private InvoiceRepository invoiceRepository;
	private ModelMapperService modelMapperService;
	private OrderedAdditionalServiceRepository orderedAdditionalServiceRepository;
	private RentalRepository rentalRepository;
	private AdditionalServiceItemRepository additionalServiceItemRepository;
	
	
	@Autowired
	public InvoiceManager(InvoiceRepository invoiceRepository, ModelMapperService modelMapperService,
			OrderedAdditionalServiceRepository orderedAdditionalServiceRepository, RentalRepository rentalRepository,
			AdditionalServiceItemRepository additionalServiceItemRepository) {
		this.invoiceRepository = invoiceRepository;
		this.modelMapperService = modelMapperService;
		this.orderedAdditionalServiceRepository = orderedAdditionalServiceRepository;
		this.rentalRepository = rentalRepository;
		this.additionalServiceItemRepository = additionalServiceItemRepository;
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		Invoice invoice= modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		invoice.setPresentDate(LocalDate.now());
		invoice.setTotalInvoicePrice(calculateTotalInvoicePrice(createInvoiceRequest.getRentalId()));
		
		
		invoiceRepository.save(invoice);
		return new SuccessResult("INVOICE ADDED");
	}


	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {
		this.invoiceRepository.deleteById(deleteInvoiceRequest.getId());
		return new SuccessResult("INVOICE DELETED");
	}

	@Override
	public DataResult<List<AdditionalServiceItem>> getAllAdditionalItems(int rentalId) {
		List<OrderedAdditionalService> orderedAdditionalServices = this.orderedAdditionalServiceRepository.findAllByRentalId(rentalId);
		List<AdditionalServiceItem> additionalServiceItems = new ArrayList<AdditionalServiceItem>();
		for (OrderedAdditionalService orderedAdditionalService : orderedAdditionalServices) {
			AdditionalServiceItem additionalServiceItem = this.additionalServiceItemRepository.findById(orderedAdditionalService.getAdditionalServiceItem().getId());
			additionalServiceItems.add(additionalServiceItem);
			
		}
		return new SuccessDataResult<List<AdditionalServiceItem>>(additionalServiceItems);
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

	private double TotalRentalAdditionalPrice(int id) {
		double totalOrderedAdditionalService=0;
		List<OrderedAdditionalService> orderedAdditionalServices=this.orderedAdditionalServiceRepository.findAllByRentalId(id);
		for(OrderedAdditionalService orderedAdditionalService: orderedAdditionalServices) {
			totalOrderedAdditionalService += orderedAdditionalService.getTotalPrice();
		}
		return totalOrderedAdditionalService;
	}
	
	private double calculateTotalInvoicePrice(int rentalId) {
		Rental rental = this.rentalRepository.findById(rentalId);
		double totalInvoicePrice = rental.getTotalPrice()+TotalRentalAdditionalPrice(rentalId);
		return totalInvoicePrice;
		
	}
}
