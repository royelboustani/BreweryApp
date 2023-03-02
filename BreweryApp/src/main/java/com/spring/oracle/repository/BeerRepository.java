package com.spring.oracle.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.spring.oracle.model.Beer;

public interface BeerRepository extends JpaRepository<Beer, Long> {
	
	  List<Beer> findByBreweryId(long brewery_id);
	  
	  Beer findByIdAndBreweryId(@Param("id") long id, @Param("BreweryId") long brewery_id);
	  
	  @Transactional
	  void deleteByBreweryId(long brewery_id);
	  
	  @Transactional
	  void deleteByIdAndBreweryId(@Param("id") long id, @Param("BreweryId") long brewery_id);
}
