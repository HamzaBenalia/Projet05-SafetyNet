package com.safetynet.alerts.service;


import com.safetynet.alerts.dto.loadData.FirestationData;
import com.safetynet.alerts.dto.loadData.InitData;
import com.safetynet.alerts.dto.loadData.MedicalRecordData;
import com.safetynet.alerts.dto.loadData.PersonData;
import com.safetynet.alerts.model.Allergy;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medicalrecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.impl.DataPopulatorServiceImpl;
import com.safetynet.alerts.service.impl.FirestationServiceImpl;
import com.safetynet.alerts.service.impl.MedicalrecordServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class DataPopulatorServiceTest {

    @Mock
    private PersonService personService;
    @Mock
    private FirestationServiceImpl firestationServiceImpl;
    @Mock
    private MedicalrecordServiceImpl medicalrecordServiceImpl;
    @Mock
    private AllergyService allergyService;

    @InjectMocks
    private DataPopulatorServiceImpl dataPopulatorServiceImpl;


    @Test
    public void testLoadData() {
        PersonData personData = new PersonData();
        personData.setFirstName("firstName");
        personData.setLastName("lastName");
        FirestationData firestationDTO = new FirestationData();
        firestationDTO.setAddress("adresse");
        firestationDTO.setStation("station");
        MedicalRecordData medicalRecordData = new MedicalRecordData();
        medicalRecordData.setBirthdate("09/06/2017");
        medicalRecordData.setMedications(Arrays.asList("medication1"));
        medicalRecordData.setFirstName(personData.getFirstName());
        medicalRecordData.setLastName(personData.getLastName());
        medicalRecordData.setAllergies(Arrays.asList("Allergie1"));

        InitData initData = new InitData();
        initData.setFirestations(Arrays.asList(firestationDTO));
        initData.setMedicalrecords(Arrays.asList(medicalRecordData));
        initData.setPersons(Arrays.asList(personData));

        doNothing().when(personService).add(any());
        doNothing().when(firestationServiceImpl).add(any());
        doNothing().when(medicalrecordServiceImpl).add(any());
        doNothing().when(allergyService).add(any());

        dataPopulatorServiceImpl.loadData(initData);

        verify(personService).add(any(Person.class));
        verify(firestationServiceImpl).add(any(Firestation.class));
        verify(medicalrecordServiceImpl).add(any(Medicalrecord.class));
        verify(allergyService).add(any(Allergy.class));
    }
}
