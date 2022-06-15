package com.kodlamaio.rentACar.business.responses.additionalServiceItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListAdditionalServiceItemResponse {
	private int id;
	private String name;
	private String description;
	private double additionalPrice;

}
