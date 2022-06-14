package com.kodlamaio.rentACar.business.request.colors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateColorRequest {
	private int id;
	private String name;
	

}
