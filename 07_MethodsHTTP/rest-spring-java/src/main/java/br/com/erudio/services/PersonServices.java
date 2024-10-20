package br.com.erudio.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.erudio.model.Person;

@Service
public class PersonServices {
	
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	public Person findById(String id) {
		
		logger.info("Finding one person!");
		Person person =  new Person();
		
		person.setId(counter.incrementAndGet());
		person.setFirstName("Carlos");
		person.setLastName("Casara");
		person.setAddress("Pinhal");
		person.setGender("Male");
		
		return person;
	}
	
	public List<Person> findAll() {
		logger.info("Finding all people!");
		List<Person> persons = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			Person person = mockPerson(i);
			persons.add(person);
		}
		
		return persons ;
	}
	
	public Person createPerson(Person person) {
		logger.info("Creating one person!");
		
		return person;
	}
	
	public Person updatePerson(Person person) {
		logger.info("Updating one person!");
		
		return person;
	}
	
	public void deletePerson(String id) {
		logger.info("Delete one person!");
	}

	private Person mockPerson(int i) {
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Firstname " + i);
		person.setLastName("Lastname  " + i);
		person.setAddress("Same address " + i);
		person.setGender("Male");
		
		return person;
	}
}
