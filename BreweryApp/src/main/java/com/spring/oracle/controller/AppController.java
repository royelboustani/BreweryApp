package com.spring.oracle.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.exception.ResourceNotFoundException;
import com.spring.oracle.model.Beer;
import com.spring.oracle.model.Brewery;
import com.spring.oracle.model.Message;
import com.spring.oracle.model.ClientOrder;
import com.spring.oracle.model.Stock;
import com.spring.oracle.model.Wholesale;
import com.spring.oracle.repository.BeerRepository;
import com.spring.oracle.repository.BreweryRepository;
import com.spring.oracle.repository.OrderRepository;
import com.spring.oracle.repository.StockRepository;
import com.spring.oracle.repository.WholesaleRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class AppController {
	
	@Autowired
	BreweryRepository breweryRepository;
	
	@Autowired
	BeerRepository beerRepository;
	
	@Autowired
	StockRepository stockRepository;
	
	@Autowired
	WholesaleRepository wholesaleRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	  @PostMapping("/clientOrder/wholesaleId/{wholesaleId}/beerId/{beerId}/quantity/{quantity}")
	  public ResponseEntity<Message> clientOrder(@PathVariable(value = "wholesaleId") long wholesaleId,
			  @PathVariable(value = "beerId") long beerId, @PathVariable(value = "quantity") int quantity) {
		  Message msg = new Message();  
		  if(quantity == 0)
		  {
		      throw new ResourceNotFoundException("The order cannot be empty");  
		  }
		  if (!wholesaleRepository.existsById(wholesaleId)) {
		    	throw new ResourceNotFoundException("The wholesaler must exist");  
		   }
		  if (orderRepository.findByBeerIdAndWholesaleIdAndQuantity(beerId, wholesaleId, quantity).size() > 0) {
		    	throw new ResourceNotFoundException("There can't be any duplicate in the order");  
		   }
		  List<Stock> stock = stockRepository.findByBeerIdAndWholesaleId(beerId, wholesaleId);
		  if(stock.size() > 0)
		  {
			  if(quantity > stock.get(0).getQuantity())
			  {
				  throw new ResourceNotFoundException("The number of beers ordered cannot be greater than the wholesaler's stock");
			  }
		  }
		  else
		  {
			  throw new ResourceNotFoundException("The beer must be sold by the wholesaler");
		  }
		  
		  double price = Double.parseDouble(beerRepository.getOne(beerId).getPrice());
		  
	       if (quantity> 10 && quantity <= 20)
	        {
	            price = quantity * price - (quantity * price * 0.1);
	            msg.setMessage("Price "+price+ " a 10% discount is applied above 10 drinks"); 

	        }
	        else if (quantity > 20)
	        {
	        	price = quantity * price - (quantity * price * 0.2);
	        	msg.setMessage("Price "+price+ " a 20% discount is applied above 20 drinks"); 
	        }
	        else {
	        	
	        	msg.setMessage("Price "+price);
	        }
		  
		  return new ResponseEntity<>(msg, HttpStatus.OK);
		    
	  }
	
	  @PostMapping("/updateWholesaleStock/wholesaleId/{wholesaleId}/beerId/{beerId}/quantity/{quantity}")
	  public ResponseEntity<Message> UpdateWholesaleStock(@PathVariable(value = "wholesaleId") long wholesaleId,
			  @PathVariable(value = "beerId") long beerId, @PathVariable(value = "quantity") int quantity) {
		  Message msg = new Message();  
		  List<Stock> stock = stockRepository.findByBeerIdAndWholesaleId(beerId, wholesaleId);
		    if(stock.size() > 0)
		    {
		    	stock.get(0).setQuantity(quantity);
		    	stockRepository.save(stock.get(0));
		    }
		    else {

			    if (!wholesaleRepository.existsById(wholesaleId)) {
				     msg.setMessage("Not found Wholesale with id = " + wholesaleId);
				     return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);}
			    if (!beerRepository.existsById(beerId)) {
			    	msg.setMessage("Not found Beer with id = " + beerId);
			    	return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);  
				    }
			    
		    	stockRepository.save(new Stock(wholesaleRepository.getOne(wholesaleId),
		    			beerRepository.getOne(beerId),quantity));
			    }
			    msg.setMessage("Wholesale Stock is updated");
			    return new ResponseEntity<>(msg, HttpStatus.CREATED);			    
		    
	  }
	
	  @PostMapping("/addBeerToWholesaleStock/wholesaleId/{wholesaleId}/beerId/{beerId}")
	  public ResponseEntity<Message> addBeerToWholesaleStock(@PathVariable(value = "wholesaleId") long wholesaleId,
			  @PathVariable(value = "beerId") long beerId) {
		  Message msg = new Message();  
		  List<Stock> stock = stockRepository.findByBeerIdAndWholesaleId(beerId, wholesaleId);
		    if(stock.size() > 0)
		    {
		    	stock.get(0).setQuantity(stock.get(0).getQuantity() + 1);
		    	stockRepository.save(stock.get(0));
		    }
		    else {

			    if (!wholesaleRepository.existsById(wholesaleId)) {
				     msg.setMessage("Not found Wholesale with id = " + wholesaleId);
				     return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);}
			    if (!beerRepository.existsById(beerId)) {
			    	msg.setMessage("Not found Beer with id = " + beerId);
			    	return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);  
				    }
			    
		    	stockRepository.save(new Stock(wholesaleRepository.getOne(wholesaleId),
		    			beerRepository.getOne(beerId),1));
			    }
			    msg.setMessage("Beer added to wholesale stock");
			    return new ResponseEntity<>(msg, HttpStatus.CREATED);			    
		    
	  }
	
	  @DeleteMapping("/deleteBreweryBeer/breweryId/{breweryId}/beerId/{beerId}")
	  public ResponseEntity<List<Beer>> deleteBreweryBeer(@PathVariable(value = "breweryId") Long breweryId,
			  @PathVariable(value = "beerId") Long beerId) {
		  Beer beer = null;
	    if (!breweryRepository.existsById(breweryId)) {
	      throw new ResourceNotFoundException("Not found Brewery with id = " + breweryId);
	      
	    }
	    beer = beerRepository.findByIdAndBreweryId(beerId, breweryId);
	    if (beer  == null) {
		      throw new ResourceNotFoundException("Brewery doesn't have a beer with id = " + beerId);
		    }

	    beerRepository.deleteByIdAndBreweryId(beerId, breweryId);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }
	
	  @PostMapping("/addBeerToBrewery/breweryId/{breweryId}")
	  public ResponseEntity<Beer> addBeer(@PathVariable(value = "breweryId") long breweryId,
	      @RequestBody Beer beerRequest) {
	    Beer beer = breweryRepository.findById(breweryId).map(brewery -> {
	    	beerRequest.setBrewery(brewery);
	      return beerRepository.save(beerRequest);
	    }).orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + breweryId));

	    return new ResponseEntity<>(beer, HttpStatus.CREATED);
	  }
	// /api/getBreweryBeers?id=1
	@GetMapping("/getBreweryBeers/breweryId/{breweryId}")
	public ResponseEntity<List<Beer>> getAllBeers(@PathVariable(value = "breweryId") long breweryId) {
		try {
			List<Beer> beers = new ArrayList<Beer>();

				beerRepository.findByBreweryId(breweryId).forEach(beers::add);

			if (beers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(beers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@GetMapping("/breweries")
	public ResponseEntity<List<Brewery>> getAllBreweries(@RequestParam(required = false) String name) {
		try {
			List<Brewery> breweries = new ArrayList<Brewery>();

			if (name == null)
				breweryRepository.findAll().forEach(breweries::add);
			else
				breweryRepository.findByName(name).forEach(breweries::add);

			if (breweries.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(breweries, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
