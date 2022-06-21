package com.kodlamaio.rentACar.business.request.addresses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressRequest {
	private int id;
	
	private String contactAdress;
	
	private String billingAdress;
	
	private int userId;
}
