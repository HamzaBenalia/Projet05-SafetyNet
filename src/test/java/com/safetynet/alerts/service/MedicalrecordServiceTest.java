package com.safetynet.alerts.service;
import com.safetynet.alerts.model.Medicalrecord;
import com.safetynet.alerts.repository.MedicalrecordRepository;
import com.safetynet.alerts.service.impl.MedicalrecordServiceImpl;
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
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalrecordServiceTest {

    @Captor
    private ArgumentCaptor<Medicalrecord> medicalrecordArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> namePosologyArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> firstNameArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> lastNameArgumentCaptor;
    @InjectMocks
    private MedicalrecordServiceImpl medicalrecordServiceImpl;
    @Mock
    private MedicalrecordRepository medicalrecordRepository;
    @Mock
    private PersonService personService;

    @Test
    public void testAddMedicalrecord() {
        Medicalrecord medicalrecord = new Medicalrecord("Hamza", "Ben", "Doliprane : 200mg", "16/01/1995");
        //when(medicalrecordRepository.getAll()).thenReturn(new ArrayList<>());

        medicalrecordServiceImpl.add(medicalrecord);

        Mockito.verify(medicalrecordRepository, times(1)).save(medicalrecordArgumentCaptor.capture());

        assertThat(medicalrecordArgumentCaptor.getValue())
                .extracting("firstName", "lastName", "namePosology", "birthDate")
                .containsExactly(medicalrecord.getFirstName(), medicalrecord.getLastName(), medicalrecord.getNamePosology(), medicalrecord.getBirthDate());
    }

    @Test
    public void testGetAllMedicalrecords() {
        Medicalrecord medicalrecord1 = new Medicalrecord("Hamza", "Ben", "Doliprane : 200mg", "15/03/1995");
        Medicalrecord medicalrecord2 = new Medicalrecord("Sara", "Hams", "Ubiprophen : 300mg", "12/12/1998");
        List<Medicalrecord> allMedicalrecords = Arrays.asList(medicalrecord1, medicalrecord2);
        when(medicalrecordRepository.getAll()).thenReturn(allMedicalrecords);

        List<Medicalrecord> result = medicalrecordServiceImpl.getAll();
        Mockito.verify(medicalrecordRepository, times(1)).getAll();
        assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(medicalrecord1));
        Assertions.assertTrue(result.contains(medicalrecord2));
    }

    @Test
    public void testGetMedicalrecorByFirstNameAndLastName() {
        // Create a list of medical records to use as test data
        Medicalrecord medicalrecord1 = new Medicalrecord("John", "Doe", "aspirin", "16/02/1995");
        List<Medicalrecord> medicalrecordList = new ArrayList<>();

        medicalrecordRepository.save(medicalrecord1);
        medicalrecordList.add(medicalrecord1);

        when(medicalrecordServiceImpl.getMedicalrecorByFirstNameAndLastName("John", "Doe")).thenReturn(medicalrecordList);

        List<Medicalrecord> result = medicalrecordServiceImpl.getMedicalrecorByFirstNameAndLastName("John", "Doe");

        // Verify that the correct medical record was returned
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, medicalrecordList);
        verify(medicalrecordRepository, times(1)).getMedicalrecorByFirstNameAndLastName("John", "Doe");

        // Verify that the medicalrecordRepository's getAll method was called
    }


    @Test
    public void testDeleteMedicalRecordByFirstNameLastNameAndNamePosology() {

        doNothing().when(medicalrecordRepository).deleteMedicalrecordByFirstNameLastNameAndPosology(anyString(),anyString(),anyString());
        medicalrecordServiceImpl.deleteMeicalrecordByFirstNameLastNameAndNamePosology("Hamza", "Benalia", "Doliprane : 200mg");
        verify(medicalrecordRepository,times(1)).deleteMedicalrecordByFirstNameLastNameAndPosology(firstNameArgumentCaptor.capture(),
                lastNameArgumentCaptor.capture(), namePosologyArgumentCaptor.capture());
        Assertions.assertEquals("Hamza",firstNameArgumentCaptor.getValue());
        Assertions.assertEquals("Benalia",lastNameArgumentCaptor.getValue());
        Assertions.assertEquals("Doliprane : 200mg",namePosologyArgumentCaptor.getValue());

    }

    @Test
    public void testUpdateMedicalrecords() {

        Medicalrecord medicalrecordToUpdate = new Medicalrecord("Hamza", "Benalia", "Doliprane : 400mg", "16/02/1995");
        String oldNamePosology = "Doliprane : 200mg";

        // Appeler la méthode à tester
        medicalrecordServiceImpl.updateMedicalrecords(medicalrecordToUpdate,oldNamePosology);

        // Vérifier que la méthode updateMedicalrecords a été appelée avec les bons paramètres
        verify(medicalrecordRepository).updateMedicalrecords(medicalrecordArgumentCaptor.capture(), namePosologyArgumentCaptor.capture());
        Medicalrecord medicalrecordCapture = medicalrecordArgumentCaptor.getValue();
        String namePosologyCapture = namePosologyArgumentCaptor.getValue();
        Assertions.assertEquals(medicalrecordToUpdate.getFirstName(),medicalrecordCapture.getFirstName());
        Assertions.assertEquals(medicalrecordToUpdate.getLastName(),medicalrecordCapture.getLastName());
        Assertions.assertEquals(medicalrecordToUpdate.getNamePosology(),medicalrecordCapture.getNamePosology());
        Assertions.assertEquals(medicalrecordToUpdate.getBirthDate(),medicalrecordCapture.getBirthDate());
        Assertions.assertEquals(oldNamePosology,namePosologyCapture);
    }

    @Test
    public void testFindByFirstNameLastNameAndPosology() {

        Medicalrecord medicalrecord1 = new Medicalrecord("John", "Doe", "Aspirin", "16/02/1995");

        medicalrecordRepository.save(medicalrecord1);

        when(medicalrecordServiceImpl.findByFirstNameLastNameAndPosology("John", "Doe", "Aspirin")).thenReturn(medicalrecord1);

        Medicalrecord result = medicalrecordServiceImpl.findByFirstNameLastNameAndPosology("John", "Doe", "Aspirin");

        // Verify that the correct medical record was returned
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result, medicalrecord1);
        verify(medicalrecordRepository, times(1)).findByFirstNameLastNameAndPosology("John", "Doe", "Aspirin");

    }


}
