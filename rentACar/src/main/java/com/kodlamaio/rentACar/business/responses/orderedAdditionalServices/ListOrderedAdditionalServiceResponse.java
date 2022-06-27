package com.kodlamaio.rentACar.business.responses.orderedAdditionalServices;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kodlamaio.rentACar.entities.concretes.AdditionalServiceItem;
import com.kodlamaio.rentACar.entities.concretes.Rental;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOrderedAdditionalServiceResponse {
	private int id;
	private int totalDays;
	private double totalPrice;
	private LocalDate pickupDate;
	private LocalDate returnDate;
	private int additionalServiceItemId;
	private int rentalId;
	
}