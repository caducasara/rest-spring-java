package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	public PersonVO findById(Long id) {
		
		logger.info("Finding one person!");
		
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		PersonVO vo =  PersonMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).getPersonById(id)).withSelfRel());
		
		return vo;
	}
	
	public List<PersonVO> findAll() {
		logger.info("Finding all people!");
		
		List<PersonVO> persons =  PersonMapper.parseListObject(repository.findAll(), PersonVO.class);
		persons
			.stream()
				.forEach(p -> p.add(linkTo(methodOn(PersonController.class).getPersonById(p.getKey())).withSelfRel()));
		
		return persons;
	}
	
	public PersonVO createPerson(PersonVO person) {
		logger.info("Creating one person!");
		
		Person entity = PersonMapper.parseObject(person, Person.class);
		
		PersonVO vo = PersonMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).getPersonById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public PersonVO updatePerson(PersonVO person) {
		logger.info("Updating one person!");
		
		Person entity = repository.findById(person.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		PersonVO vo = PersonMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).getPersonById(person.getKey())).withSelfRel());
		
		return vo;
	}
	
	public void deletePerson(Long id) {
		logger.info("Delete one person!");
		
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		repository.delete(entity);
	}
}
