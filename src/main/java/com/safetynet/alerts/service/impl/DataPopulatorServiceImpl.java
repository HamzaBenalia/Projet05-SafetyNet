package com.safetynet.alerts.service.impl;
import com.safetynet.alerts.dto.loadData.InitData;
import com.safetynet.alerts.model.Allergy;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medicalrecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.AllergyService;
import com.safetynet.alerts.service.DataPopulatorService;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataPopulatorServiceImpl implements DataPopulatorService {


    @Autowired
    private PersonService personService;
    @Autowired
    private FirestationServiceImpl firestationServiceImpl;
    @Autowired
    private MedicalrecordServiceImpl medicalrecordServiceImpl;
    @Autowired
    private AllergyService allergyService;

    public void loadData(InitData initData) {
        List<Person> persons = new ArrayList<>();
        initData.getPersons().forEach(currentPerson -> {
            Person person = Person.builder().lastName(currentPerson.getLastName())
                    .address(currentPerson.getAddress())
                    .city(currentPerson.getCity())
                    .zip(currentPerson.getZip())
                    .phone(currentPerson.getPhone())
                    .firstName(currentPerson.getFirstName())
                    .email(currentPerson.getEmail()).build();
            initData.getMedicalrecords().forEach(m -> {
                if (currentPerson.getFirstName().equals(m.getFirstName()) && currentPerson.getLastName().equals(m.getLastName())) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    LocalDate birthdate = LocalDate.parse(m.getBirthdate(), formatter);
                    String birthdateStr = birthdate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    person.setBirthdate(birthdateStr);
                }
            });
            persons.add(person);
            personService.add(person);
        });
        initData.getFirestations().forEach(f -> {
            String address = f.getAddress();
            String stationNumber = f.getStation();
            Firestation firestation = new Firestation(address, stationNumber);
            firestationServiceImpl.add(firestation);
        });

        initData.getMedicalrecords().forEach(m -> {
            String firstName = m.getFirstName();
            String lastName = m.getLastName();
            for (Person p : persons) {
                if (firstName.equals(p.getFirstName()) && lastName.equals(p.getLastName())) {
                    m.getMedications().forEach(med -> {
                        Medicalrecord medicalrecord = new Medicalrecord();
                        medicalrecord.setFirstName(p.getFirstName());
                        medicalrecord.setLastName(p.getLastName());
                        medicalrecord.setNamePosology(med);
                        medicalrecordServiceImpl.add(medicalrecord);
                    });
                    m.getAllergies().forEach(allergyCurrent -> {
                        Allergy allergy = new Allergy();
                        allergy.setNameAllergy(allergyCurrent);
                        allergy.setLastName(p.getLastName());
                        allergy.setFirstName(p.getFirstName());
                        allergyService.add(allergy);
                    });
                    break;
                }
            }
        });
    }
}
