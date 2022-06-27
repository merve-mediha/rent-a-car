package com.kodlamaio.rentACar.business.request.orderedAdditionalServices;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderedAdditionalServiceRequest {
	private int id;
	private int totalDays;
	private double totalPrice;
	private LocalDate pickupDate;
	private LocalDate returnDate;
	private int additionalServiceItemId;
	private int rentalId;
}