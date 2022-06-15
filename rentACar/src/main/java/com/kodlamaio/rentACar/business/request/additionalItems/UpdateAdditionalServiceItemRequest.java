package com.kodlamaio.rentACar.business.request.additionalItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalServiceItemRequest {
	private int id;
	private String name;
	private String description;
	private double additionalPrice;
}
