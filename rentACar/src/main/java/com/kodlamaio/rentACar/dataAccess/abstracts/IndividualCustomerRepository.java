package com.kodlamaio.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.entities.concretes.IndividualCustomer;

public interface IndividualCustomerRepository extends JpaRepository<IndividualCustomer,	Integer> {
	IndividualCustomer findByIdentityNumber(String identityNumber);
	IndividualCustomer findByEmail(String email);
	IndividualCustomer findById(int id);
	
}