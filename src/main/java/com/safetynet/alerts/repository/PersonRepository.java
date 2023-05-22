package com.safetynet.alerts.repository;
import com.safetynet.alerts.model.Person;
import java.util.List;
public interface PersonRepository {


    public void save(Person person);

    public List<Person> getAll();

    public void deletePerson(Person person);

    public void updatePerson(Person updatedPerson);

    public List<Person> getPersonByName(String lastName);

    public List<Person> getPersonByAddress(String address);

    public List<Person> getPersonByPhone(String phone);
}
