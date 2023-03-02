package com.spring.oracle.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "brewery")
public class Brewery implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BREWERYID_SEQ")
	@SequenceGenerator(name = "BREWERYID_SEQ", sequenceName = "BREWERYID_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "name")
	private String name;

	public Brewery() {

	}

	public Brewery(String breweryName) {
		this.name = breweryName;
	}

	public long getId() {
		return id;
	}

	public String getBreweryName() {
		return name;
	}

	public void setBreweryName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Brewery [id=" + id + ", name=" + name + "]";
	}

}
