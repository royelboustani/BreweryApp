package com.spring.oracle.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.spring.oracle.model.Stock;


public interface StockRepository extends JpaRepository<Stock, Long> {
	
	List<Stock> findByBeerIdAndWholesaleId(@Param("BeerId") long beerId, @Param("WholesaleId") long wholesaleId);
	  
}
