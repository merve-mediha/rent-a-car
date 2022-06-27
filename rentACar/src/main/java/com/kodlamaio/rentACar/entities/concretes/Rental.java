package com.kodlamaio.rentACar.entities.concretes;


import java.time.LocalDate;
import java.util.Date;
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="rentals")
public class Rental {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="pickup_date")
	private LocalDate pickupDate;
	
	@Column(name="return_date")
	private LocalDate returnDate;
	
	@Column(name="total_days")
	private long totalDays;
	
	@Column(name="total_price")
	private double totalPrice;
	
	@ManyToOne
	@JoinColumn(name="car_id")
	private Car car;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="pickupCity_id",referencedColumnName="id")
	private City pickupCityId;
	
	@ManyToOne
	@JoinColumn(name="returnCity_id",referencedColumnName="id")
	private City returnCityId;
	
	@OneToMany (mappedBy="rental")
	private List<OrderedAdditionalService> OrderedAdditionalServices;
	
	@OneToMany(mappedBy="rental")
	private List<Invoice> invoices;
	

}
