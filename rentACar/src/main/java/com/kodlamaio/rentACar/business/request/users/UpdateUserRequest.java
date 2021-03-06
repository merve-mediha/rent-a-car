package com.kodlamaio.rentACar.business.request.users;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
	private int id;
	
	@NotBlank
	@NotNull
	@Min(3)
	private String firstName;
	
	@NotBlank
	@NotNull
	@Size(min=2,max=50,message="Must be at least three characters.")
	private String lastName;
	
	@NotBlank
	@NotNull
	@Pattern(regexp = "[0-9]{11}", message = "Length must be 11")
	private String identityNumber;
	
	@NotBlank
	@NotNull
	private int birthYear;
	
	@NotBlank
	@NotNull
	@Pattern(regexp = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@" 
        + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$", message="Write according to the rules name@domain.com")
	private String email;
	
	@NotBlank
	@NotNull
	private String password;
	
	
}
