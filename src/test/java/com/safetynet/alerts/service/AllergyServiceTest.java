package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Allergy;
import com.safetynet.alerts.repository.AllergyRepository;
import com.safetynet.alerts.repository.PersonRepository;
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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AllergyServiceTest {

    @InjectMocks
    AllergyService allergyService;

    @Mock
    AllergyRepository allergyRepository;

    @Captor
    private ArgumentCaptor<Allergy> allergyArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> nameAllergyArgumentCaptor;

    @Mock
    private PersonRepository personRepository;


    @Test
    public void testAddAllergy() {
        Allergy allergy = new Allergy("John", "Boyd", "Pollen");
        //when(allergyRepository.getAll()).thenReturn(new ArrayList<>());

        allergyService.add(allergy);

        Mockito.verify(allergyRepository, times(1)).save(allergyArgumentCaptor.capture());

        assertThat(allergyArgumentCaptor.getValue())
                .extracting("firstName", "lastName", "nameAllergy")
                .containsExactly(allergy.getFirstName(), allergy.getLastName(), allergy.getNameAllergy());


    }

    @Test
    public void testGetAllAllergies() {
        // Create an allergy for testing
        Allergy allergy = new Allergy("John", "Doe", "Pollen");

        // Add the allergy to the service
        allergyService.add(allergy);

        // Inject the list of allergies into the class to be tested
        when(allergyRepository.getAll()).thenReturn(Collections.singletonList(allergy));

        // Verify that the method getAll() returns the list of allergies
        List<Allergy> allergies = allergyService.getAll();
        Assertions.assertEquals(1, allergies.size());
        Assertions.assertEquals("John", allergies.get(0).getFirstName());
        Assertions.assertEquals("Doe", allergies.get(0).getLastName());
        Assertions.assertEquals("Pollen", allergies.get(0).getNameAllergy());
    }


    @Test
    public void testDeleteAllergyByFirstNameLastNameAndNameAllergy() {
        // Créer une liste d'allergies
        List<Allergy> allergies = new ArrayList<>();
        allergies.add(new Allergy("John", "Doe", "Pollen"));
        allergies.add(new Allergy("Jane", "Doe", "Peanuts"));


        //when(allergyRepository.getAll()).thenReturn(allergies);

        // Supprimer une allergie
        allergyService.deleteMeicalrecordByFirstNameLastNameAndNamePosology("John", "Doe", "Pollen");

        // Vérifier que l'allergie a bien été supprimée

        Assertions.assertFalse(allergies.contains("John"));
    }


    @Test
    public void testUpdateAllergy() {
        // Créer une liste d'allergies
        String oldAllergy = "Peanuts";
        Allergy allergy = new Allergy("John", "Doe", "Pollen");
        doNothing().when(allergyRepository).updateAllergy(any(), any());

        allergyService.updateAllergy(allergy, oldAllergy);

        verify(allergyRepository, times(1)).updateAllergy(allergyArgumentCaptor.capture(), nameAllergyArgumentCaptor.capture());
        Allergy allergyCapture = allergyArgumentCaptor.getValue();
        String oldAllergyNameCapture = nameAllergyArgumentCaptor.getValue();

        Assert.assertEquals(oldAllergy, oldAllergyNameCapture);
        Assert.assertEquals(allergy.getNameAllergy(), allergyCapture.getNameAllergy());
        Assert.assertEquals(allergy.getFirstName(), allergyCapture.getFirstName());
        Assert.assertEquals(allergy.getLastName(), allergyCapture.getLastName());
    }
}





