package com.spring.oracle.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import com.spring.oracle.model.Brewery;
import com.spring.oracle.repository.BreweryRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JPAUnitTest {
	
	  @Autowired
	  private TestEntityManager entityManager;
	  

	  @Autowired
	  BreweryRepository breweryRepository;
	  
	  @Test
	  public void should_update_brewery_by_id() {
	    Brewery brewery1 = new Brewery("Brewery One");
	    entityManager.persist(brewery1);

	    Brewery brewery2 = new Brewery("Brewery Two");
	    entityManager.persist(brewery2);

	    Brewery updatedbrewery = new Brewery("Updated Brewery");

	    Brewery brewery = breweryRepository.findById(brewery2.getId()).get();
	    brewery.setBreweryName(updatedbrewery.getBreweryName());

	    breweryRepository.save(brewery);

	    Brewery checkTut = breweryRepository.findById(brewery2.getId()).get();
	    
	    assertThat(checkTut.getBreweryName()).isEqualTo(brewery2.getId());

	  }

	  @Test
	  public void should_delete_brewery_by_id() {
		  Brewery brewery1 = new Brewery("Brewery One");
	    entityManager.persist(brewery1);

	    Brewery brewery2 = new Brewery("Brewery Two");
	    entityManager.persist(brewery2);

	    Brewery brewery3 = new Brewery("Brewery Three");
	    entityManager.persist(brewery3);

	    breweryRepository.deleteById(brewery2.getId());

	    List<Brewery> tutorials = breweryRepository.findAll();

	    assertThat(tutorials).hasSize(2).contains(brewery1, brewery3);
	  }
	  
	  @Test
	  public void should_find_brewery_by_id() {
		Brewery brewery1 = new Brewery("Brewery One");
	    entityManager.persist(brewery1);

	    Brewery brewery2 = new Brewery("Brewery Two");
	    entityManager.persist(brewery2);

	    Brewery foundTutorial = breweryRepository.findById(brewery2.getId()).get();

	    assertThat(foundTutorial).isEqualTo(brewery2);
	  }
	  
	  @Test
	  public void should_find_all_brewery() {
	    Brewery brewery1 = new Brewery("Brewery One");
	    entityManager.persist(brewery1);

	    Brewery brewery2 = new Brewery("Brewery Two");
	    entityManager.persist(brewery2);

	    Brewery brewery3 = new Brewery("Brewery Four");
	    entityManager.persist(brewery3);

	    List<Brewery> brewerylist = breweryRepository.findAll();

	    assertThat(brewerylist).hasSize(3).contains(brewery1, brewery2, brewery3);
	  }

}
