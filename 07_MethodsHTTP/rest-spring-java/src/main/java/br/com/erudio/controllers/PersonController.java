package br.com.erudio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.model.Person;
import br.com.erudio.services.PersonServices;

@RestController
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonServices service;
	//private PersonServices person = new PersonServices();
	
	@RequestMapping(
				value = "/{id}", 
				method = RequestMethod.GET, 
				produces = MediaType.APPLICATION_JSON_VALUE
			)
	public Person getPersonById(
				@PathVariable(value = "id") String id
			) {
		return service.findById(id);
	}
	
	@RequestMapping(
				method = RequestMethod.GET, 
				produces = MediaType.APPLICATION_JSON_VALUE
			)
	public List<Person> getAllPeople(){
		return service.findAll();
	}
	
	@RequestMapping(
				method = RequestMethod.POST, 
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE
			)
	public Person createPerson(@RequestBody Person person) {
		return service.createPerson(person);
	}
	
	@RequestMapping(
			method = RequestMethod.PUT, 
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
		)
	public Person updatePerson(@RequestBody Person person) {
		return service.updatePerson(person);
	}
	
	@RequestMapping(
			value = "/{id}", 
			method = RequestMethod.DELETE
		)
	public void deletePersonById(
				@PathVariable(value = "id") String id
			) {
		service.deletePerson(id);
	}
}
