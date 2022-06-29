package com.kodlamaio.rentACar.business.request.addresses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAddressRequest {
	private int id;
	private String contactAddress;
	private String billingAddress;
	private int customerId;

}
