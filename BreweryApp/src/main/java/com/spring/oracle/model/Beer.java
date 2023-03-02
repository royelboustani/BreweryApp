package com.spring.oracle.model;
 
import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
 
/*
* This is our model class and it corresponds to Beer table in database
*/
@Entity
@Table(name="beer")
public class Beer implements Serializable{
	  
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BEER_SEQ")
	@SequenceGenerator(name = "BEER_SEQ", sequenceName = "BEER_SEQ", allocationSize = 1)
    private long id;
 
    @Column(name="Name")
    private String name; 
    
    @Column(name="Alcohol")
    private String alcohol;
    
    @Column(name="Price")
    private String price;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brewery_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Brewery brewery;
 
 
    public Beer() {
        super();
    }
    public Beer(int i, String Name, Brewery brewery, String alcohol, String price) {
        super();
        this.id = i;
        this.name = Name;
        this.brewery = brewery;
        this.alcohol = alcohol;
        this.price = price;

    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getBeerName() {
        return name;
    }
    public void setBeerName(String beerName) {
        this.name = beerName;
    }
    public Brewery getBrewery() {
        return brewery;
    }
    public void setBrewery(Brewery brewery) {
        this.brewery = brewery;
    }
    
    public String getAlcohol() {
        return alcohol;
    }
    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }
    
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
 
}