package com.rayzhou.helloword.dao;

import com.rayzhou.helloword.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class PersonDataAccessService implements PersonDao {
	private static List<Person> DB = new ArrayList<>();

	@Override
	public int insertPerson(UUID id, Person person) {
		DB.add(new Person(id, person.getName()));
		return 1;
	}

	@Override
	public List<Person> selectAllPerson() {
		return DB;
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		return DB.stream().filter(p -> p.getId().equals(id)).findFirst();
	}

	@Override
	public int deletePersonById(UUID id) {
		Optional<Person> person = selectPersonById(id);
		if (person.isPresent()) {
			DB.remove(person.get());
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public int updatePersonById(UUID id, Person person) {
		return selectPersonById(id).map(p -> {
			int indexToRemove = DB.indexOf(p);
			if(indexToRemove >= 0) {
				DB.set(indexToRemove, person);
				return 1;
			}
			else {
				return 0;
			}
		}).orElse(0);
	}
}
