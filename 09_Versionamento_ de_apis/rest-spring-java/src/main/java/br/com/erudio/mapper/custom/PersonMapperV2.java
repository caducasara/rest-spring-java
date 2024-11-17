package br.com.erudio.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.model.Person;

@Service
public class PersonMapperV2 {

	public PersonVOV2 convertEntityToVO(Person entity) {
		PersonVOV2 personVO = new PersonVOV2();
		
		personVO.setId(entity.getId());
		personVO.setFirstName(entity.getFirstName());
		personVO.setLastName(entity.getLastName());
		personVO.setAddress(entity.getAddress());
		personVO.setGender(entity.getGender());
		personVO.setBirthday(new Date());
		
		return personVO;
	}
	
	public Person converVOToEntity(PersonVOV2 personVO) {
		Person entity = new Person();
		
		entity.setId(personVO.getId());
		entity.setFirstName(personVO.getFirstName());
		entity.setLastName(personVO.getLastName());
		entity.setAddress(personVO.getAddress());
		entity.setGender(personVO.getGender());
		
		return entity;
	}
}
