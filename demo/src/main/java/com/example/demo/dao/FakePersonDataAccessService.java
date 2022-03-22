package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Person;

/*repository will set the label of implementation. For instance, This class is set with the label
 * "fakeDao" which can be called in the in the service layer using @Qualifier
 * See com.example.demo.service.PersonService.java constructor for more info*/
@Repository("fakeDao") //
public class FakePersonDataAccessService implements PersonDao{

	private static List<Person> DB = new ArrayList();
	
	@Override
	public int insertPerson(UUID id, Person person) {
		// TODO Auto-generated method stub
		DB.add(new Person(id, person.getName()));
		return 1;
	}

	@Override
	public List<Person> selectAllPeople() {
		// TODO Auto-generated method stub
		return DB;
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		// TODO Auto-generated method stub
		return DB.stream()
				.filter(person -> person.getId().equals(id))
				.findFirst();
	}

	@Override
	public int deletePersonById(UUID id) {
		Optional<Person> personTest = selectPersonById(id);
		if(personTest.isEmpty())
			return 0;
		DB.remove(personTest.get());
		return 1;
	}

	@Override
	public int updatePersonById(UUID id, Person person) {
		return selectPersonById(id)
				.map(p -> {
					int indexOfPersonToDelete = DB.indexOf(person);
					if(indexOfPersonToDelete >= 0) {
						DB.set(indexOfPersonToDelete, person);
						return 1;
					}
				})
				.orElse(0);
	}

}
