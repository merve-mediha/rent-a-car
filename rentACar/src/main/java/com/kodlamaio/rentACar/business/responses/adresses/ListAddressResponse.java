package com.kodlamaio.rentACar.business.responses.adresses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListAddressResponse {
	private int id;
	
	private String contactAdress;
	
	private String billingAdress;
	
	private int userId;

}
