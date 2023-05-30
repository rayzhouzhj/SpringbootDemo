package com.rayzhou.helloword.dao;

import com.rayzhou.helloword.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonPostgresDataAccessService implements PersonDao {
	private static List<Person> DB = new ArrayList<>();

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public PersonPostgresDataAccessService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insertPerson(UUID id, Person person) {
		DB.add(new Person(id, person.getName()));
		return 1;
	}

	@Override
	public List<Person> selectAllPerson() {
		String sql = "select id, name from person";
		return jdbcTemplate.query(sql, (resultset, i) -> new Person(UUID.fromString(resultset.getString("id")), resultset.getString("name")));
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		String sql = "select id, name from person where id = ?";
		Person person = jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultset, i) -> new Person(UUID.fromString(resultset.getString("id")), resultset.getString("name")));
		return Optional.ofNullable(person);
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
