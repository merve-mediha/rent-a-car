package com.kodlamaio.rentACar.business.request.maintenances;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMaintenanceRequest {
	private int id;
	private String description;
	private LocalDate dataSent;
	private LocalDate dateReturned;
	private int carId;

}
