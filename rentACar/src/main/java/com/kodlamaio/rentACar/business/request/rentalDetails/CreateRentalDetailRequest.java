package com.kodlamaio.rentACar.business.request.rentalDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalDetailRequest {
	private int id;
	private double totalRentalPrice;
	private int rentalId;
	private int additionalServiceId;
}
