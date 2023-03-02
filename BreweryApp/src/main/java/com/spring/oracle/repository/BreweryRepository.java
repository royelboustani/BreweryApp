package com.spring.oracle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.oracle.model.Brewery;


public interface BreweryRepository extends JpaRepository<Brewery, Long> {
	List<Brewery> findByName(String name);
}
