package com.kodlamaio.rentACar.business.request.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateUserRequest {
	private String firstName;
	private String lastName;
	private String identityNumber;
	private int birthYear;
	private String email;
	private String password;
	
	
	
}
