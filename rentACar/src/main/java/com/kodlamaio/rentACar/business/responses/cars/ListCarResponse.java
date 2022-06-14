package com.kodlamaio.rentACar.business.responses.cars;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCarResponse {
	private String description;
	private double dailyPrice;
	private double kilometer;
	private String carPlate;
	private  int state;
	private int colorId;
	private int brandId;
	
}
