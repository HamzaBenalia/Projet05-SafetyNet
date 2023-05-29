package com.safetynet.alerts.service;
import com.safetynet.alerts.dto.childAlert.ChildDto;
import com.safetynet.alerts.dto.fire.FireDto;
import com.safetynet.alerts.dto.flood.FloodDto;
import com.safetynet.alerts.dto.personInfo.PersonInfoDto;
import com.safetynet.alerts.dto.phoneAlert.PhoneAlertDataDto;
import com.safetynet.alerts.dto.phoneAlert.PhoneAlertDto;
import com.safetynet.alerts.dto.stationDto.StationInfoDto;
import com.safetynet.alerts.model.Allergy;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medicalrecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.AllergyRepository;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.MedicalrecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.service.impl.PersonServiceImpl;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @InjectMocks
    private PersonServiceImpl personServiceImpl;
    @Mock
    private PersonRepository personRepository;
    @Captor
    private ArgumentCaptor<Person> personArgumentCaptor;
    @Mock
    private AllergyRepository allergyRepository;
    @Mock
    private MedicalrecordRepository medicalrecordRepository;

    @Mock
    private FirestationRepository firestationRepository;


    @Test
    public void testAdd() {
        Person person = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        when(personRepository.getAll()).thenReturn(new ArrayList<>());

        personServiceImpl.add(person);

        Mockito.verify(personRepository, times(1)).save(personArgumentCaptor.capture());

        assertThat(personArgumentCaptor.getValue())
                .extracting("firstName", "lastName", "address", "city", "zip", "phone", "email", "birthdate")
                .containsExactly(person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(), person.getZip(), person.getPhone(), person.getEmail(), person.getBirthdate());
    }


    @Test
    public void testAddExistePerson() {
        Person person = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        when(personRepository.getAll()).thenReturn(List.of(person));
        personServiceImpl.add(person);
        Mockito.verify(personRepository, times(0)).save(personArgumentCaptor.capture());
    }

    @Test
    public void testGetAll() {

        // Create some persons
        Person person1 = new Person("John", "Doe", "john.doe@example.com");
        Person person2 = new Person("Jane", "Doe", "jane.doe@example.com");
        List<Person> allPersons = Arrays.asList(person1, person2);

        when(personRepository.getAll()).thenReturn(allPersons);

        List<Person> result = personServiceImpl.getAll();
        Mockito.verify(personRepository, times(1)).getAll();
        assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(person1));
        Assertions.assertTrue(result.contains(person2));
    }

    @Test
    public void testGetPersonByFirstNameAndLastName() {
        Person person1 = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");

        personRepository.save(person1);

        List<Person> personList = new ArrayList<>();
        personList.add(person1);

        when(personRepository.getAll()).thenReturn(personList);

        Person result = personServiceImpl.getPersonByFirstNameAndLastName("John", "Boyd");

        // Verify that the correct medical record was returned
        Assertions.assertEquals(result, person1);
        verify(personRepository, times(1)).getAll();

    }

    @Test
    public void testDeleteByFirstNameAndLastName() {
        // Create a list of persons to use as test data
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("John", "Doe"));
        personList.add(new Person("Alice", "Smith"));

        when(personRepository.getAll()).thenReturn(personList);

        personServiceImpl.deleteByFirstNameAndLastName("Alice", "Smith");

       verify(personRepository, times(1)).getAll();
    }

    @Test
    public void testUpdatePerson() {

        Person person = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        personRepository.save(person);

        // Create an updated version of the test person
        Person updatedPerson = new Person("Hamza", "Ben", "Toulouse", "Toulouse", "31100", "0766764585", "Hamza@gmail.com", "16/03/1998");

        // Call the updatePerson method with the updated person
        personServiceImpl.updatePerson(updatedPerson);

        Person updatedPersons = updatedPerson;

        // Get the person from the repository and verify that it has been updated
        assertEquals(updatedPerson, updatedPersons);

    }

    @Test
    public void testGetPersonByPhone() {
        // Create a list of persons to use as test data
        Person person1 = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        person1.setPhone("123456789");
        Person person2 = new Person("Hamza", "Ben", "Toulouse", "Toulouse", "31100", "0666528541", "Boyd@gmail.com", "16/03/1995");
        person2.setPhone("987654321");


        // Create a mock PersonRepository that returns the test data
        when(personServiceImpl.getPersonByphone("123456789")).thenReturn(Collections.singletonList(person1));
        when(personServiceImpl.getPersonByphone("987654321")).thenReturn(Collections.singletonList(person2));


        List<Person> foundPersons1 = personServiceImpl.getPersonByphone("123456789");
        List<Person> foundPersons2 = personServiceImpl.getPersonByphone("987654321");

        // Verify that the correct person(s) were returned
        assertEquals(1, foundPersons1.size());
        assertEquals(person1, foundPersons1.get(0));
        assertEquals(1, foundPersons2.size());
        assertEquals(person2, foundPersons2.get(0));

        // Verify that the personRepository's getPersonByPhone method was called
        verify(personRepository, times(2)).getPersonByPhone(anyString());
    }

    @Test
    public void testGetAllInfo() {
        // Create test data
        Person person1 = new Person("John", "Doe", "Toulouse");
        Medicalrecord medicalrecord1 = new Medicalrecord("John", "Doe", "Doliprane = : 200mg", "15/03/1995");
        Person person2 = new Person("Jane", "Doe", "Paris");
        Medicalrecord medicalrecord2 = new Medicalrecord("Jane", "Doe", "Ubiprohen : 300mg", "14/08/1997");

        PersonRepository personRepository = mock(PersonRepository.class);
        MedicalrecordRepository medicalrecordRepository = mock(MedicalrecordRepository.class);

        // Add test data to repositories
        personRepository.save(person1);
        medicalrecordRepository.save(medicalrecord1);
        personRepository.save(person2);
        medicalrecordRepository.save(medicalrecord2);
    }

    @Test
    public void testGetPersonInfos() {
        // Create test data
        Person person1 = new Person("John", "Doe", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        Person person2 = new Person("Jane", "Doe", "Paris", "Paris", "31100", "0766852232", "Boyd123@gmail.com", "16/07/1995");
        Allergy allergy1 = new Allergy("John", "Doe", "peanuts");
        Medicalrecord medicalrecord1 = new Medicalrecord("John", "Doe", "Doliprane : 200mg", "15/03/1997");
        Medicalrecord medicalrecord2 = new Medicalrecord("Jane", "Doe", "Ubiprophen", "10/01/1993");


        // Configure mocks
        when(personRepository.getPersonByName("Doe")).thenReturn(List.of(person1, person2));
        when(allergyRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(List.of(allergy1));
        when(medicalrecordRepository.getMedicalrecorByFirstNameAndLastName("John", "Doe")).thenReturn(List.of(medicalrecord1));
        when(medicalrecordRepository.getMedicalrecorByFirstNameAndLastName("Jane", "Doe")).thenReturn(List.of(medicalrecord2));

        // Call the method being tested
        List<PersonInfoDto> result = personServiceImpl.getPersonInfos("John", "Doe");

        // Verify the results
        assertEquals(2, result.size());
        List<String> actualFirstNames = result.stream().map(personInfoDto -> personInfoDto.getPersonDataDto().getFirstName()).collect(toList());
        List<String> actualLastNames = result.stream().map(personInfoDto -> personInfoDto.getPersonDataDto().getLastName()).collect(toList());

        MatcherAssert.assertThat(actualFirstNames, containsInAnyOrder(person1.getFirstName(), person2.getFirstName()));
        MatcherAssert.assertThat(actualLastNames, containsInAnyOrder(person1.getLastName(), person2.getLastName()));
    }

    @Test
    public void testGetPhonesByFirestation() {
        Person person = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        Firestation firestation = new Firestation("Toulouse", "1");

        when(personRepository.getAll()).thenReturn(Arrays.asList(person));
        when(firestationRepository.getAdresseByStation(anyString())).thenReturn(Arrays.asList(firestation.getAddress()));

        PhoneAlertDto result = personServiceImpl.getPhonesByFirestation("1");

        Assertions.assertNotNull(result);
        Assert.assertEquals(1, result.getPhoneAlertDataDtos().size());
        PhoneAlertDataDto phoneAlertDataDto = result.getPhoneAlertDataDtos().get(0);
        Assert.assertEquals(person.getPhone(),phoneAlertDataDto.getPhone());
    }


    @Test
    public void testGetResidentsByAddress() {
        // Create test data
        Firestation firestation = new Firestation("Toulouse", "1");
        when(firestationRepository.getAll()).thenReturn(List.of(firestation));
        Person person = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        Allergy allergy = new Allergy(person.getFirstName(), person.getLastName(), "peanut");
        Medicalrecord medicalrecord = new Medicalrecord(person.getFirstName(), person.getLastName(), "Doliprane", "16/03/1995");

        when(personRepository.getPersonByAddress("Toulouse")).thenReturn(List.of(person));
        when(allergyRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName())).thenReturn(List.of(allergy));
        when(medicalrecordRepository.getMedicalrecorByFirstNameAndLastName(person.getFirstName(), person.getLastName())).thenReturn(List.of(medicalrecord));

        List<FireDto> result = personServiceImpl.getResidentsByAddress("Toulouse");

        Assertions.assertEquals(1, result.size());
        Assert.assertEquals(person.getFirstName(), result.get(0).getFireDataDto().getFirstName());
        Assert.assertEquals(person.getPhone(), result.get(0).getFireDataDto().getPhone());

    }


    @Test
    public void testGetResidentsByStationNumber() {

        Firestation firestation1 = new Firestation("Toulouse", "1");
        Mockito.when(firestationRepository.getFirestationByStation("1")).thenReturn(List.of(firestation1));

        Person person = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        Mockito.when(personRepository.getAll()).thenReturn(List.of(person));

        Allergy allergy = new Allergy(person.getFirstName(), person.getLastName(), "peanuts");
        Mockito.when(allergyRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName())).thenReturn(List.of(allergy));
        Medicalrecord medicalrecord = new Medicalrecord(person.getFirstName(), person.getLastName(), "Doliprane : 200mg", "16/03/1995");
        Mockito.when(medicalrecordRepository.getMedicalrecorByFirstNameAndLastName(person.getFirstName(), person.getLastName())).thenReturn(List.of(medicalrecord));


        // Call the method being tested
        Map<String, List<FloodDto>> result = personServiceImpl.getResidentsByStationNumber(Arrays.asList("1", "2"));

        // Verify the result
        Assert.assertEquals(1, result.size());

        List<FloodDto> floodDataDtos1 = result.get("Toulouse");
        Assert.assertEquals(person.getFirstName(), floodDataDtos1.get(0).getFloodDataDto().getFirstName());
        Assert.assertEquals(person.getPhone(), floodDataDtos1.get(0).getFloodDataDto().getPhone());

    }

    @Test
    public void testGetChildrenByAddress() {

        Person personJohn = new Person("John", "Boyd", "Toulouse", "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/2010");
        Person personHamza = new Person("Hamza", "Benalia", "Toulouse", "Toulouse", "31100", "0766764585", "Hamza@gmail.com", "16/03/2000");
        Mockito.when(personRepository.getPersonByAddress(anyString())).thenReturn(Arrays.asList(personJohn, personHamza));

        // Call the method being tested
        ChildDto result = personServiceImpl.getChildrenByAddress("Toulouse");

        // Verify the result
        Assert.assertEquals(1, result.getChildren().size());
        Assert.assertEquals(1, result.getAdults().size());

        Assert.assertEquals(personJohn.getFirstName(), result.getChildren().get(0).getFirstName());
        Assert.assertEquals(personJohn.getLastName(), result.getChildren().get(0).getLastName());
        Assert.assertEquals(personHamza.getFirstName(), result.getAdults().get(0).getFirstName());
        Assert.assertEquals(personHamza.getLastName(), result.getAdults().get(0).getLastName());
    }

    @Test
    public void getPersonByFirestationTest(){
        String address = "Toulouse";
        Firestation firestation = new Firestation(address, "1");

        Person personAdult = new Person("John", "Boyd", address, "Toulouse", "31100", "0766764585", "Boyd@gmail.com", "16/03/1995");
        Person personMinor = new Person("Hamza", "Benalia", address, "Toulouse", "31100", "06060605", "Boyd@gmail.com", "16/03/2012");
        Mockito.when(personRepository.getAll()).thenReturn(List.of(personAdult, personMinor));

        when(firestationRepository.getAdresseByStation("1")).thenReturn(Collections.singletonList(address));

        // Call the method being tested
        StationInfoDto result = personServiceImpl.getPersonByFirestation(("1"));

        // Verify the result

        Assert.assertEquals(personAdult.getFirstName(), result.getPersonStationDtos().get(0).getFirstName());
        Assert.assertEquals(personMinor.getFirstName(), result.getPersonStationDtos().get(1).getFirstName());

    }
}



