package com.safetynet.alerts.service.impl;
import com.safetynet.alerts.dto.childAlert.ChildDataDto;
import com.safetynet.alerts.dto.childAlert.ChildDto;
import com.safetynet.alerts.dto.fire.FireDataDto;
import com.safetynet.alerts.dto.fire.FireDto;
import com.safetynet.alerts.dto.flood.FloodDataDto;
import com.safetynet.alerts.dto.flood.FloodDto;
import com.safetynet.alerts.dto.personInfo.PersonDataDto;
import com.safetynet.alerts.dto.personInfo.PersonInfoDto;
import com.safetynet.alerts.dto.phoneAlert.PhoneAlertDataDto;
import com.safetynet.alerts.dto.phoneAlert.PhoneAlertDto;
import com.safetynet.alerts.dto.stationDto.PersonStationDto;
import com.safetynet.alerts.dto.stationDto.StationInfoDto;
import com.safetynet.alerts.model.Allergy;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medicalrecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.MedicalrecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.repository.impl.AllergyRepositoryImpl;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.utils.CalculateAge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MedicalrecordRepository medicalrecordRepository;
    @Autowired
    private AllergyRepositoryImpl allergyRepository;
    @Autowired
    private FirestationRepository firestationRepository;


    @Override
    public void add(Person person) {
        if (getPersonByFirstNameAndLastName(person.getFirstName(), person.getLastName()) != null) {
            return;
        }
        personRepository.save(person);
    }

    @Override
    public List<Person> getAll() {
        return personRepository.getAll();
    }

    @Override
    public Person getPersonByFirstNameAndLastName(String firstName, String lastName) {
        List<Person> persons = personRepository.getAll();
        for (Person person : persons) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                return person;
            }
        }
        return null;
    }

    @Override
    public void deleteByFirstNameAndLastName(String firstName, String lastName) {
        List<Person> persons = personRepository.getAll();
        for (Iterator<Person> iterator = persons.iterator(); iterator.hasNext();) {
            Person person = iterator.next();
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                iterator.remove();
            }
        }
    }

    @Override
    public void updatePerson(Person updatePerson) {
        personRepository.updatePerson(updatePerson);
    }

    @Override
    public List<PersonInfoDto> getPersonInfos(String firstName, String lastName) {
        CalculateAge calculateAge = new CalculateAge();
        List<PersonInfoDto> personInfoDtos = new ArrayList<>();
        List<Person> personList = personRepository.getPersonByName(lastName);
        for (Person person : personList) {
            List<Allergy> allergieList = allergyRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            List<Medicalrecord> medicalrecordList = medicalrecordRepository.getMedicalrecorByFirstNameAndLastName(person.getFirstName(), person.getLastName());
            PersonDataDto personDataDto = new PersonDataDto();
            personDataDto.setAddress(person.getAddress());
            LocalDate birthDate = calculateAge.getDate(person.getBirthdate());
            int age = calculateAge.calculateAge(birthDate);
            personDataDto.setAge(age);
            personDataDto.setEmail(person.getEmail());
            personDataDto.setFirstName(person.getFirstName());
            personDataDto.setLastName(person.getLastName());
            PersonInfoDto personInfoDto = new PersonInfoDto(personDataDto, allergieList, medicalrecordList);
            personInfoDtos.add(personInfoDto);
        }
        return personInfoDtos;
    }

    @Override
    public List<Person> getPersonByphone(String phone) {
        return personRepository.getPersonByPhone(phone);
    }

    @Override
    public StationInfoDto getPersonByFirestation(String firestationNumber) {
        CalculateAge calculateAge = new CalculateAge();
        StationInfoDto stationInfoDto = new StationInfoDto();
        int minors = 0;
        int adults = 0;
        List<PersonStationDto> personStationDtos = new ArrayList<>();
        List<Person> personList = personRepository.getAll();
        List<String> addresses = firestationRepository.getAdresseByStation(firestationNumber);
        for (Person person : personList) {
            if (addresses.contains(person.getAddress())) {
                PersonStationDto personStationDto = new PersonStationDto();
                personStationDto.setAddress(person.getAddress());
                personStationDto.setFirstName(person.getFirstName());
                personStationDto.setLastName(person.getLastName());
                personStationDto.setPhone(person.getPhone());
                personStationDtos.add(personStationDto);
                LocalDate birthDate = calculateAge.getDate(person.getBirthdate());
                int age = calculateAge.calculateAge(birthDate);
                if (age < 18) {
                    minors++;
                } else {
                    adults++;
                }

            }
        }
        stationInfoDto.setPersonStationDtos(personStationDtos);
        stationInfoDto.setMinor(minors);
        stationInfoDto.setAdult(adults);
        return stationInfoDto;
    }

    @Override
    public PhoneAlertDto getPhonesByFirestation(String firestationNumber) {
        PhoneAlertDto phoneAlertDto = new PhoneAlertDto();
        List<PhoneAlertDataDto> phoneAlertDataDtos = new ArrayList<>();
        List<Person> personList = personRepository.getAll();
        List<String> addresses = firestationRepository.getAdresseByStation(firestationNumber);
        for (Person person : personList) {
            if (addresses.contains(person.getAddress())) {
                PhoneAlertDataDto phoneAlertDataDto = new PhoneAlertDataDto();
                phoneAlertDataDto.setPhone(person.getPhone());
                phoneAlertDataDtos.add(phoneAlertDataDto);
            }
        }
        phoneAlertDto.setPhoneAlertDataDtos(phoneAlertDataDtos);

        return phoneAlertDto;
    }

    @Override
    public List<FireDto> getResidentsByAddress(String address) {
        CalculateAge calculateAge = new CalculateAge();
        List<FireDto> fireDataDtos = new ArrayList<>();
        for (Firestation firestation : firestationRepository.getAll()) {
            for (Person person : personRepository.getPersonByAddress(address)) {
                if (firestation.getAddress().equals(address)) {
                    List<Allergy> allergieList = allergyRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                    List<Medicalrecord> medicalrecordList = medicalrecordRepository.getMedicalrecorByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                    FireDataDto fireDataDto = new FireDataDto();
                    LocalDate birthDate = calculateAge.getDate(person.getBirthdate());
                    int age = calculateAge.calculateAge(birthDate);
                    fireDataDto.setAge(String.valueOf(age));
                    fireDataDto.setPhone(person.getPhone());
                    fireDataDto.setFirstName(person.getFirstName());
                    fireDataDto.setStationNumber(firestation.getStation());
                    FireDto fireDto = new FireDto(fireDataDto, allergieList, medicalrecordList);
                    fireDataDtos.add(fireDto);
                }
            }

        }

        return fireDataDtos;
    }

    @Override
    public Map<String, List<FloodDto>> getResidentsByStationNumber(List<String> stations) {
        CalculateAge calculateAge = new CalculateAge();
        Map<String, List<FloodDto>> map = new HashMap<>();
        for (String station : stations) {

            for (Firestation firestation1 : firestationRepository.getFirestationByStation(station)) {
                List<FloodDto> floodDataDtos = new ArrayList<>();
                for (Person person : personRepository.getAll()) {
                    if (firestation1.getAddress().equals(person.getAddress())) {
                        Optional<FloodDto> optionalFloodDto = floodDataDtos.stream().filter(floodDto -> floodDto.getFloodDataDto().getFirstName().equals(person.getFirstName())
                                && floodDto.getFloodDataDto().getLastName().equals(person.getLastName())).findFirst();
                        if (optionalFloodDto.isEmpty()) {
                            List<Allergy> allergieList = allergyRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                            List<Medicalrecord> medicalrecordList = medicalrecordRepository.getMedicalrecorByFirstNameAndLastName(person.getFirstName(), person.getLastName());
                            FloodDataDto floodDataDto = new FloodDataDto();
                            LocalDate birthDate = calculateAge.getDate(person.getBirthdate());
                            int age = calculateAge.calculateAge(birthDate);
                            floodDataDto.setAge(String.valueOf(age));
                            floodDataDto.setPhone(person.getPhone());
                            floodDataDto.setFirstName(person.getFirstName());
                            FloodDto floodDto1 = new FloodDto(floodDataDto, allergieList, medicalrecordList);
                            floodDataDtos.add(floodDto1);
                        }
                    }
                }
                map.put(firestation1.getAddress(), floodDataDtos);
            }
        }
        return map;
    }

    @Override
    public ChildDto getChildrenByAddress(String address) {
        CalculateAge calculateAge = new CalculateAge();
        ChildDto childDto = new ChildDto();
        List<ChildDataDto> childDataDtos = new ArrayList<>();
        List<Person> adults = new ArrayList<>();
        List<Person> persons = personRepository.getPersonByAddress(address);
        Optional<Person> optionalPerson = persons.stream().filter(person -> calculateAge.calculateAge(calculateAge.getDate(person.getBirthdate())) < 18).findFirst();
        if (optionalPerson.isPresent()) {
            for (Person person : persons) {
                int age = calculateAge.calculateAge(calculateAge.getDate(person.getBirthdate()));
                if (age < 18) {
                    ChildDataDto childDataDto = new ChildDataDto();
                    childDataDto.setAge(age);
                    childDataDto.setFirstName(person.getFirstName());
                    childDataDto.setLastName(person.getLastName());
                    childDataDtos.add(childDataDto);
                } else {
                    adults.add(person);
                }
            }
        }
        childDto.setChildren(childDataDtos);
        childDto.setAdults(adults);
        return childDto;
    }
}
