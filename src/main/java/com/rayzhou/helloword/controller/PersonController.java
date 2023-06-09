package com.rayzhou.helloword.controller;

import com.rayzhou.helloword.model.Person;
import com.rayzhou.helloword.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/person")
public class PersonController {
	private final PersonService personService;

	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}

	@PostMapping
	public void addPerson(@Valid @NonNull @RequestBody Person person) {
		personService.addPerson(person);
	}

	@GetMapping
	public List<Person> getAllPeople() {
		return personService.getAllPeople();
	}

	@GetMapping(path = "{id}")
	public Person getPersonById(@PathVariable("id") UUID id) {
		return personService.findPerson(id).orElse(null);
	}


	@PutMapping(path = "{id}")
	public int updatePerson(@PathVariable("id") UUID id, @Valid @NonNull @RequestBody Person person) {
		return personService.updatePerson(id, person);
	}

	@DeleteMapping(path = "{id}")
	public int deletePerson(@PathVariable("id") UUID id) {
		return personService.deletePerson(id);
	}
}
