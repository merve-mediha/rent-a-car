package com.kodlamaio.rentACar.business.request.cars;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
	private int id;
	private String description;
	private double dailyPrice;
	private double kilometer;
	private String carPlate;
	private int state;
	private int brandId;
	private int colorId;
	
	
	
	
	
	

}
