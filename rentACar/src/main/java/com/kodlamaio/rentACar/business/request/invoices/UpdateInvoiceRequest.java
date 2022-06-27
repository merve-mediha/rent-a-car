package com.kodlamaio.rentACar.business.request.invoices;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {
	private int id;
	private int invoiceNumber;
	private double totalInvoicePrice;
	private LocalDate currentDate;
	private int rentalId;

}
