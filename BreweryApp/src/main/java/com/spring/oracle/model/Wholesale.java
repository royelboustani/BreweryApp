package com.spring.oracle.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "wholesale")
public class Wholesale implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WHOLESALE_SEQ")
	@SequenceGenerator(name = "WHOLESALE_SEQ", sequenceName = "WHOLESALE_SEQ", allocationSize = 1)
	private long id;

	@Column(name = "name")
	private String name;

	public Wholesale() {
		 super();
	}

	public Wholesale(long id, String wholesaleName) {
		super();
		this.id=id;
		this.name = wholesaleName;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getBreweryName() {
		return name;
	}
	
	public void setBreweryName(String name) {
		this.name = name;
	}



	@Override
	public String toString() {
		return "wholesale [id=" + id + ", name=" + name + "]";
	}

}
