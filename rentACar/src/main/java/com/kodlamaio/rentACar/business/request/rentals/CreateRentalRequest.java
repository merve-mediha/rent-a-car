package com.kodlamaio.rentACar.business.request.rentals;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {
	private Date pickupDate;
	private Date returnDate;
	private long totalDays;
	private double totalPrice;
	private int carId;
	private int userId;
	private int pickupCityId;
	private int returnCityId;

}
