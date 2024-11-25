package br.com.erudio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.services.PersonServices;
import br.com.erudio.uitl.MediaType;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {
	
	@Autowired
	private PersonServices service;
	//private PersonServices person = new PersonServices();
	
	@GetMapping(
				value = "/{id}",
				produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }
			)
	public PersonVO getPersonById(
				@PathVariable(value = "id") Long id
			) {
		return service.findById(id);
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	public List<PersonVO> getAllPeople(){
		return service.findAll();
	}
	
	@PostMapping(
				consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
				produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }
			)
	public PersonVO createPerson(@RequestBody PersonVO person) {
		return service.createPerson(person);
	}
	
	@PutMapping(
			consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML },
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML }
		)
	public PersonVO updatePerson(@RequestBody PersonVO person) {
		return service.updatePerson(person);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deletePersonById(
				@PathVariable(value = "id") Long id
			) {
		service.deletePerson(id);
		
		return ResponseEntity.noContent().build();
	}
}
