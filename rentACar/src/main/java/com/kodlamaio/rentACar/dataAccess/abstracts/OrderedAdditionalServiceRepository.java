package com.kodlamaio.rentACar.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.rentACar.entities.concretes.OrderedAdditionalService;

public interface OrderedAdditionalServiceRepository extends JpaRepository<OrderedAdditionalService, Integer>{
	List<OrderedAdditionalService> getAllByRentalId(int rentalId);
	OrderedAdditionalService findById(int id);

}
