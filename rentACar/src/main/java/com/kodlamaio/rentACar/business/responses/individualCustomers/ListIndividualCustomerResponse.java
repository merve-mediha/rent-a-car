package com.kodlamaio.rentACar.business.responses.individualCustomers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListIndividualCustomerResponse {
	private int individualCustomerId;
	private String firstName;
	private String lastName;
	private String identityNumber;
	private int birthYear;
	private int customerNumber;
	private String email;
	private String password;
}

