package com.kodlamaio.rentACar.business.request.corporateCustomers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {
	private int corporateCustomerId;
	private String corporateCustomerName;
	private String taxNumber;
	private String email;
	private String password;
	
}
