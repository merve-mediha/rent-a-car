package com.kodlamaio.rentACar.business.responses.addresses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListAddressResponse {
	private int id;
	private String contactAddress;
	private String billingAddress;
	private int customerId;

}
