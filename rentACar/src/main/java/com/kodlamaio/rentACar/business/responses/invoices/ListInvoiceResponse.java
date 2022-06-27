package com.kodlamaio.rentACar.business.responses.invoices;

import java.time.LocalDate;
import java.util.List;

import com.kodlamaio.rentACar.entities.concretes.AdditionalServiceItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ListInvoiceResponse {
	private int id;
	private int rentalId;
	private int invoiceNumber;
	private String carNumberPlate;
	private String carDescription;
	private int orderedAdditionalServiceId;
	private String firstName;
	private LocalDate currentDate;
	private double totalInvoicePrice;
	private List<AdditionalServiceItem> additionalServiceItems;
	
}
