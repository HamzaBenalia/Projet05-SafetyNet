package com.safetynet.alerts.repository;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.impl.PersonRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class PersonRepositoryTest {


    @Test
    public void SaveTest() {
        Person person1 = new Person("John", "Boyd");
        PersonRepositoryImpl personRepositoryImpl = new PersonRepositoryImpl();
        personRepositoryImpl.save(person1);
        assertTrue(personRepositoryImpl.getAll().contains(person1));
    }

    @Test
    public void getAllTest() {
        PersonRepositoryImpl personRepositoryImpl = new PersonRepositoryImpl();
        Person person1 = new Person("John", "Boyd");
        Person person2 = new Person("Jacob", "Boyd");
        personRepositoryImpl.save(person1);
        personRepositoryImpl.save(person2);
        List<Person> allPersons = personRepositoryImpl.getAll();
        assertEquals(2, allPersons.size());
        assertTrue(allPersons.contains(person1));
        assertTrue(allPersons.contains(person2));
    }

    @Test
    public void deletePersonTest() {
        PersonRepositoryImpl personRepositoryImpl = new PersonRepositoryImpl();

        Person person1 = new Person("John", "Boyd");
        Person person2 = new Person("Jane", "Doe");
        personRepositoryImpl.save(person1);
        personRepositoryImpl.save(person2);

        // Vérifie que les deux personnes ont été ajoutées à la liste persons
        assertEquals(2, personRepositoryImpl.getAll().size());

        // Supprime person1
        personRepositoryImpl.deletePerson(person1);

        // Vérifie que person1 a été supprimé et que la taille de la liste est réduite de 1
        assertFalse(personRepositoryImpl.getAll().contains(person1));
        assertEquals(1, personRepositoryImpl.getAll().size());
    }


    @Test
    public void testGetPersonByName() {
        PersonRepositoryImpl personRepositoryImpl = new PersonRepositoryImpl();

        Person person1 = new Person("John", "Boyd");
        personRepositoryImpl.save(person1);

        List<Person> result = personRepositoryImpl.getPersonByName("Boyd");
        assertEquals(1, result.size());
        assertEquals(person1, result.get(0));

        List<Person> emptyResult = personRepositoryImpl.getPersonByName("Unknown");
        assertTrue(emptyResult.isEmpty());
    }

    @Test
    public void testGetPersonByAddress() {
        PersonRepositoryImpl personRepositoryImpl = new PersonRepositoryImpl();

        Person person1 = new Person("John", "Boyd", "1509 Culver St");
        Person person2 = new Person("Foster", "Shepard", "748 Townings Dr");
        Person person3 = new Person("Jacob", "Boyd", "1509 Culver St");
        personRepositoryImpl.save(person1);
        personRepositoryImpl.save(person2);
        personRepositoryImpl.save(person3);

        // Vérifier que la méthode retourne les personnes attendues
        List<Person> personsByAddress = personRepositoryImpl.getPersonByAddress("1509 Culver St");
        Assertions.assertEquals(2, personsByAddress.size());
        Assertions.assertTrue(personsByAddress.contains(person1));
        Assertions.assertTrue(personsByAddress.contains(person3));
    }


    @Test
    public void testGetPersonByPhone() {
        PersonRepositoryImpl personRepositoryImpl = new PersonRepositoryImpl();


        // Add some test data
        Person person1 = new Person("John", "Boyd");
        person1.setPhone("555-1234");
        Person person2 = new Person("Jane", "Doe");
        person2.setPhone("555-5678");
        personRepositoryImpl.save(person1);
        personRepositoryImpl.save(person2);

        // Test with a valid phone number
        List<Person> result1 = personRepositoryImpl.getPersonByPhone("555-1234");
        assertEquals(1, result1.size());
        assertTrue(result1.contains(person1));

        // Test with an invalid phone number
        List<Person> result2 = personRepositoryImpl.getPersonByPhone("555-9999");
        assertEquals(0, result2.size());
    }


    @Test
    public void testUpdatePerson() {
        PersonRepositoryImpl personRepositoryImpl = new PersonRepositoryImpl();

        Person person = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/2010");
        personRepositoryImpl.save(person);

        Person personUpdate = new Person("John", "Boyd", "Grenoble", "Grenoble", "38000", "0700000000", "toto@gmail.com", "16/03/2011");

        personRepositoryImpl.updatePerson(personUpdate);

        List<Person> result = personRepositoryImpl.getAll();
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(personUpdate.getFirstName(), result.get(0).getFirstName());
        Assertions.assertEquals(personUpdate.getLastName(), result.get(0).getLastName());
        Assertions.assertEquals(personUpdate.getAddress(), result.get(0).getAddress());
        Assertions.assertEquals(personUpdate.getCity(), result.get(0).getCity());
        Assertions.assertEquals(personUpdate.getZip(), result.get(0).getZip());
        Assertions.assertEquals(personUpdate.getPhone(), result.get(0).getPhone());
        Assertions.assertEquals(personUpdate.getEmail(), result.get(0).getEmail());
        Assertions.assertEquals(personUpdate.getBirthdate(), result.get(0).getBirthdate());
    }

}

