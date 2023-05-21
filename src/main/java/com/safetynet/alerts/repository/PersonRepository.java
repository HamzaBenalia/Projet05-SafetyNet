package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medicalrecord;
import com.safetynet.alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {
    private final List<Person> persons = new ArrayList<>();
    private final List<Firestation> firestations = new ArrayList<>();
    private final List<Medicalrecord> medicalrecords = new ArrayList<>();
    @Autowired
    MedicalrecordRepository medicalrecordRepository;
    AllergyRepository allergyRepository;

    public void save(Person person) {
        persons.add(person);
    }

    public List<Person> getAll() {
        return persons;
    }


    public void deletePerson(Person person) {
        persons.remove(person);
    }

    public void updatePerson(Person updatedPerson) {
        for (Person person : persons) {
            if (person.getFirstName().equals(updatedPerson.getFirstName()) && person.getLastName().equals(updatedPerson.getLastName())) {
                person.setAddress(updatedPerson.getAddress());
                person.setEmail(updatedPerson.getEmail());
                person.setCity(updatedPerson.getCity());
                person.setPhone(updatedPerson.getPhone());
                person.setZip(updatedPerson.getZip());
                person.setBirthdate(updatedPerson.getBirthdate());
            }
        }
    }

    public List<Person> getPersonByName(String lastName) {
        List<Person> personList = new ArrayList<>();
        for (Person person : persons) {
            if (person.getLastName().equals(lastName)) {
                personList.add(person);
            }
        }
        return personList;
    }

    public List<Person> getPersonByAddress(String address) {
        List<Person> personList = new ArrayList<>();
        for (Person person : persons) {
            if (person.getAddress().equals(address)) {
                personList.add(person);
            }
        }
        return personList;
    }

    public List<Person> getPersonByPhone(String phone) {
        List<Person> personList = new ArrayList<>();
        for (Person person : persons) {
            if (person.getPhone().equals(phone)) {
                personList.add(person);

            }
        }
        return personList;
    }

}