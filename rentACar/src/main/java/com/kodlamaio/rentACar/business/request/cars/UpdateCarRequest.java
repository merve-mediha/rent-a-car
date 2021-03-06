package com.kodlamaio.rentACar.business.request.cars;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	private int id;
	private String description;
	private double dailyPrice;
	private double kilometer;
	private String carPlate;
	private int brandId;
	private int colorId;
	private int minFindexScore;
	
}
