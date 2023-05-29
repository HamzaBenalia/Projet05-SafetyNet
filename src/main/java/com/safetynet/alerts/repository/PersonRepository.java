package com.safetynet.alerts.repository;
import com.safetynet.alerts.model.Person;
import java.util.List;

public interface PersonRepository {


    void save(Person person);

    List<Person> getAll();

    void deletePerson(Person person);

    void updatePerson(Person updatedPerson);

    List<Person> getPersonByName(String lastName);

    List<Person> getPersonByAddress(String address);

    List<Person> getPersonByPhone(String phone);
}
