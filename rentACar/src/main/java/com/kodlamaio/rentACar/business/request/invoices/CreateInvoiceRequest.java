package com.kodlamaio.rentACar.business.request.invoices;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kodlamaio.rentACar.entities.concretes.Rental;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
	private int id;
	private int invoiceNumber;
	private double totalInvoicePrice;
	private LocalDate currentDate;
	private int rentalId;
}
