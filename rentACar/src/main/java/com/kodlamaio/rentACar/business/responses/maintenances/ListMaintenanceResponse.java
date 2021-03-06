package com.kodlamaio.rentACar.business.responses.maintenances;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListMaintenanceResponse {
	private int id;
	private String description;
	private LocalDate sentDate;
	private LocalDate ReturnedDate;
	private int carId;

}
