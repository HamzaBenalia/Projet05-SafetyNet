package com.safetynet.alerts.repository;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.impl.FirestationRepositoryImpl;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FirestationRepositoryTest {

    @Test
    public void testSaveFirestation() {
        FirestationRepositoryImpl firestationRepository = new FirestationRepositoryImpl();

        Firestation firestation = new Firestation("1", "123 Main St");

        firestationRepository.save(firestation);

        assertTrue(firestationRepository.getAll().contains(firestation));
    }

    @Test
    public void testGetAll() {
        // Création de deux objets Firestation
        Firestation firestation1 = new Firestation("1", "address1");
        Firestation firestation2 = new Firestation("2", "address2");

        FirestationRepositoryImpl firestationRepository = new FirestationRepositoryImpl();
        firestationRepository.save(firestation1);
        firestationRepository.save(firestation2);

        List<Firestation> firestations = firestationRepository.getAll();
        assertEquals(2, firestations.size());
        assertTrue(firestations.contains(firestation1));
        assertTrue(firestations.contains(firestation2));
    }

    @Test
    public void deleteFirestationTest() {
        FirestationRepositoryImpl firestationRepository = new FirestationRepositoryImpl();

        Firestation firestation1 = new Firestation("1", "1st street");
        Firestation firestation2 = new Firestation("2", "2nd street");

        firestationRepository.save(firestation1);
        firestationRepository.save(firestation2);

        firestationRepository.deleteFirestation(firestation1);

        List<Firestation> firestations = firestationRepository.getAll();
        assertFalse(firestations.contains(firestation1));
        assertTrue(firestations.contains(firestation2));
    }

    @Test
    public void testUpdateFirestation() {
        FirestationRepositoryImpl firestationRepository = new FirestationRepositoryImpl();
        Firestation firestation1 = new Firestation("123 Main St", "1");
        firestationRepository.save(firestation1);

        Firestation updatedFirestation = new Firestation("123 Main St", "2");
        firestationRepository.updateFirestation(updatedFirestation);

        List<Firestation> firestations = firestationRepository.getAll();
        assertEquals(1, firestations.size());
        assertEquals(updatedFirestation.getStation(), firestations.get(0).getStation());
    }

    @Test
    public void testGetAdresseByStation() {
        FirestationRepositoryImpl firestationRepository = new FirestationRepositoryImpl();
        Firestation firestation1 = new Firestation("123 Main St", "1");
        Firestation firestation2 = new Firestation("456 Oak Ave", "1");
        Firestation firestation3 = new Firestation("789 Elm Rd", "2");
        firestationRepository.save(firestation1);
        firestationRepository.save(firestation2);
        firestationRepository.save(firestation3);

        List<String> result = firestationRepository.getAdresseByStation("1");
        List<String> expected = Arrays.asList("123 Main St", "456 Oak Ave");
        assertEquals(expected, result);
    }

    @Test
    public void testGetFirestationByStation() {
        FirestationRepositoryImpl firestationRepository = new FirestationRepositoryImpl();
        Firestation firestation1 = new Firestation("123 Main St", "1");
        Firestation firestation2 = new Firestation("456 Oak Ave", "2");
        Firestation firestation3 = new Firestation("789 Elm Rd", "3");
        firestationRepository.save(firestation1);
        firestationRepository.save(firestation2);
        firestationRepository.save(firestation3);

        List<Firestation> result = firestationRepository.getFirestationByStation("1");
        List<Firestation> expected = List.of(firestation1);
        assertEquals(expected, result);
    }

    @Test
    public void testgetStationByAddress() {
        FirestationRepositoryImpl firestationRepository = new FirestationRepositoryImpl();
        Firestation firestation1 = new Firestation("123 Main St", "1");
        Firestation firestation2 = new Firestation("456 Oak Ave", "1");
        Firestation firestation3 = new Firestation("789 Elm Rd", "2");
        firestationRepository.save(firestation1);
        firestationRepository.save(firestation2);
        firestationRepository.save(firestation3);

        List<String> result = firestationRepository.getStationByAddress("123 Main St");
        List<String> expected = List.of("1");
        assertEquals(expected, result);
    }

}
