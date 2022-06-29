package com.kodlamaio.rentACar.business.request.corporateCustomers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCorporateCustomerRequest {
	private String corporateCustomerName;
	private String taxNumber;
	private String email;
	private String password;
	
}
