package com.kodlamaio.rentACar.business.request.rentals;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {
	private LocalDate pickupDate;
	private LocalDate returnDate;
	private long totalDays;
	private double totalPrice;
	private int carId;
	private int customerId;
	private int pickupCityId;
	private int returnCityId;
	private int individualCustomerId;
}
