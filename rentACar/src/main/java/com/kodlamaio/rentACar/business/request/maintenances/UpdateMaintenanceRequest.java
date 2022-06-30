package com.kodlamaio.rentACar.business.request.maintenances;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMaintenanceRequest {
	private int id;
	private String description;
	private LocalDate sentDate;
	private LocalDate returnedDate;
	private int carId;

}
