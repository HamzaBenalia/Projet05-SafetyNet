package com.safetynet.alerts.service;


import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.service.impl.FirestationServiceImpl;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {
    @Captor
    ArgumentCaptor<Firestation> firestationArgumentCaptor;
    @Captor
    ArgumentCaptor<String> firestationNumberArgumentCaptor;
    @InjectMocks
    FirestationServiceImpl firestationServiceImpl;
    @Mock
    private FirestationRepository firestationRepository;
    @Mock
    private PersonRepository personRepository;

    @Test
    public void testAddFirestation() {
        Firestation firestation = new Firestation("Toulouse", "1");
        when(firestationRepository.getAll()).thenReturn(new ArrayList<>());

        firestationServiceImpl.add(firestation);

        Mockito.verify(firestationRepository, times(1)).save(firestationArgumentCaptor.capture());

        assertThat(firestationArgumentCaptor.getValue())
                .extracting("address", "station")
                .containsExactly(firestation.getAddress(), firestation.getStation());

    }


    @Test
    public void testGetAll() {
        // Create a list of fire stations to use as test data
        Firestation firestation1 = new Firestation("Toulouse", "1");
        Firestation firestation2 = new Firestation("Montpellier", "2");
        List<Firestation> allFirestations = Arrays.asList(firestation1, firestation2);

        when(firestationRepository.getAll()).thenReturn(allFirestations);

        List<Firestation> result = firestationServiceImpl.getAll();
        Mockito.verify(firestationRepository, times(1)).getAll();
        assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(firestation1));
        Assertions.assertTrue(result.contains(firestation2));
    }


    @Test
    public void testGetFirestationByAddress() {
        // Create test data
        Firestation firestation1 = new Firestation("123 Main St", "1");
        Firestation firestation2 = new Firestation("456 Elm St", "2");

        List<Firestation> firestationList = new ArrayList<>();

        firestationList.add(firestation1);
        firestationList.add(firestation2);

        firestationRepository.save(firestation1);
        firestationRepository.save(firestation2);


        when(firestationServiceImpl.getAll()).thenReturn(firestationList);

        Firestation result1 = firestationServiceImpl.getFirestationByAddress("123 Main St");
        Firestation result2 = firestationServiceImpl.getFirestationByAddress("13 route LosAngeles");

        assertNotNull(result1);
        assertNull(result2);
        assertEquals("1", result1.getStation());
    }


    @Test
    public void testDeleteFirestationByAddress() {
        // Create a list of firestations to use as test data
        Firestation firestation1 = new Firestation("1st Avenue", "1");
        Firestation firestation2 = new Firestation("2nd Avenue", "2");

        // Add the test data to the repository
        firestationRepository.save(firestation1);
        firestationRepository.save(firestation2);

        List<Firestation> firestationList = new ArrayList<>();
        firestationList.add(firestation1);
        firestationList.add(firestation2);
        when(firestationRepository.getAll()).thenReturn(firestationList);

        firestationServiceImpl.deleteFirestationByAddress("1st Avenue");

        assertEquals(1, firestationList.size());
    }

    @Test
    public void testUpdateFirestation() {
        // Create a list of firestations to use as test data
        Firestation firestation = new Firestation("1st Avenue", "1");

        firestationServiceImpl.updateFirestation(firestation);

        verify(firestationRepository).updateFirestation(firestationArgumentCaptor.capture());
        Firestation firestationCapture = firestationArgumentCaptor.getValue();
        Assertions.assertEquals(firestation.getAddress(), firestationCapture.getAddress());
        Assertions.assertEquals(firestation.getStation(), firestationCapture.getStation());
    }

    @Test
    public void testGetFirestationAddressByStation() {
        // Create a list of firestations to use as test data
        String stationNumber = "1";
        String address = "1st Avenue";

        when(firestationRepository.getAdresseByStation(anyString())).thenReturn(List.of(address));

        List<String> result = firestationServiceImpl.getFirestationAddressByStation(stationNumber);

        verify(firestationRepository).getAdresseByStation(firestationNumberArgumentCaptor.capture());
        Assertions.assertEquals(stationNumber, firestationNumberArgumentCaptor.getValue());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(address, result.get(0));
}

}
