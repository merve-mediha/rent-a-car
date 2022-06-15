package com.kodlamaio.rentACar.business.responses.additionalServices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListAdditionalServiceResponse {
	private int id;
	private int additionalItemId;
	private int rentalId;
}