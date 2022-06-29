package com.kodlamaio.rentACar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cars")

public class Car {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@NotBlank
//	@Size(min=2,max=20)
	@Column(name="description")
	private String description;
	
//	@Min(value=100)
	@NotEmpty
	@Column(name="dailyPrice")
	private double dailyPrice;
	
	
	@ManyToOne
	@JoinColumn(name="brand_id")
	private Brand brand;
	
	
	@ManyToOne
	@JoinColumn(name="color_id")
	private Color color;
	
	
	@OneToMany(mappedBy="car")  //bakım için
	private List<Maintenance>maintenances;
	
	@OneToMany(mappedBy="car")  //kiradakiler için	
	private List<Rental>rentals;
	
	@ManyToOne
	@JoinColumn(name="city_id")
	private City city;
	
	@Column(name="carPlate")
	private String carPlate;
	
	@Column(name="kilometer")
	private double kilometer;

	@Column(name="state")  //1 Available 2 Maintenance 3 Rent
	private int state;
	
	@Column(name="minFindexScore")
	private int minFindexScore;

	

	
	

}
