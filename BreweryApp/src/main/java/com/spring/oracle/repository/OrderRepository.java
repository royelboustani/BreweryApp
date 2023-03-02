package com.spring.oracle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.spring.oracle.model.ClientOrder;


public interface OrderRepository extends JpaRepository<ClientOrder, Long> {
	
	@Query(value = "SELECT * FROM CLIENTORDER WHERE BEER_ID = ?1 AND WHOLESALE_ID = ?2 AND QUANTITY = ?3", nativeQuery = true)
	List<ClientOrder> findByBeerIdAndWholesaleIdAndQuantity(long beerId, long wholesaleId, int quantity);
	
}
