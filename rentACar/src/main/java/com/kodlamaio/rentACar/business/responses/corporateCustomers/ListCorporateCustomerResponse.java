package com.kodlamaio.rentACar.business.responses.corporateCustomers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCorporateCustomerResponse {
	private int corporateCutomerId;
	private String corporateCustomerName;
	private String taxNumber;
	private String email;
	private String password;
}
