package com.spring.oracle.model;
 
import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
 
/*
* This is our model class and it corresponds to Wholesaler table in database
*/
@Entity
@Table(name="clientorder")
public class ClientOrder implements Serializable{
 
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_SEQ")
	@SequenceGenerator(name = "ORDER_SEQ", sequenceName = "ORDER_SEQ", allocationSize = 1)
	private long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wholesale_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Wholesale wholesale;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "beer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Beer beer;
    
    @Column(name="quantity")
    private int quantity;

 
    public ClientOrder() {
        super();
    }
    
    public ClientOrder(Wholesale wholesale, Beer beer, int quantity) {
        super();
        this.wholesale = wholesale;
        this.beer = beer;
        this.quantity = quantity;

    }
    public Beer getBeerId() {
        return beer;
    }
    public void setBeerId(Beer beer) {
        this.beer = beer;
    }
    public Wholesale getWholesaleId() {
        return wholesale;
    }
    public void setWholesaleId(Wholesale wholesale) {
        this.wholesale = wholesale;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
 
}