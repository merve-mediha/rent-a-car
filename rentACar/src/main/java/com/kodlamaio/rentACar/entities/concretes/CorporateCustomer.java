package com.kodlamaio.rentACar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper=false)

@Table(name="corporate_customers")
public class CorporateCustomer extends Customer {
	
	@Column(name="corporate_customer_id",insertable=false, updatable=false)
	private int corporateCutomerId;
	
	@Column(name="corporate_customer_name")
	private String corporateCustomerName;
	
	
	@Column(name="tax_number")
	private String taxNumber;
	
}
