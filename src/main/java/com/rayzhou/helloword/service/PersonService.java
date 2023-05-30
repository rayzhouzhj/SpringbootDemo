package com.rayzhou.helloword.service;

import com.rayzhou.helloword.dao.PersonDao;
import com.rayzhou.helloword.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {
	private final PersonDao personDao;

	@Autowired
	public PersonService(@Qualifier("postgres") PersonDao personDao) {
		this.personDao = personDao;
	}

	public int addPerson(Person person){
		return personDao.insertPerson(person);
	}

	public List<Person> getAllPeople() {
		return personDao.selectAllPerson();
	}

	public Optional<Person> findPerson(UUID id) {
		return personDao.selectPersonById(id);
	}

	public int deletePerson(UUID id) {
		return personDao.deletePersonById(id);
	}

	public int updatePerson(UUID id, Person person) {
		return personDao.updatePersonById(id, person);
	}
}
