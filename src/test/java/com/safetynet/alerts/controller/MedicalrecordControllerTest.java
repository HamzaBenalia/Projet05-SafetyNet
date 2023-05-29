package com.safetynet.alerts.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Medicalrecord;
import com.safetynet.alerts.service.DataPopulatorService;
import com.safetynet.alerts.service.MedicalrecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
public class MedicalrecordControllerTest {

    @MockBean
    MedicalrecordService medicalrecordService;
    @MockBean
    private DataPopulatorService dataPopulatorService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void medicalRecordController_createMedicalRecord_returnCreated() throws Exception {

        Medicalrecord medicalrecord = new Medicalrecord();
        medicalrecord.setFirstName("hamza");
        medicalrecord.setLastName("ben");
        medicalrecord.setBirthDate("16/05/1995");
        medicalrecord.setNamePosology("Morphine : 500mg");

        String json = objectMapper.writeValueAsString(medicalrecord);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/medicalrecord").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();
        verify(medicalrecordService, times(1)).add(any(Medicalrecord.class));
    }

    @Test
    public void medicalRecordController_getAllTest_returnedAllMedicalrecord() throws Exception {
        List<Medicalrecord> medicalrecordList = Arrays.asList(
                new Medicalrecord("Hamza", "ben", "Aspirine : 300mg", "14/01/1995"),
                new Medicalrecord("Sara", "ben", "Aznol : 200mg", "18/11/1997")
        );

        when(medicalrecordService.getAll()).thenReturn(medicalrecordList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/medicalrecord")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("Hamza"))
                .andExpect(jsonPath("$[0].lastName").value("ben"))
                .andExpect(jsonPath("$[0].namePosology").value("Aspirine : 300mg"))
                .andExpect(jsonPath("$[0].birthDate").value("14/01/1995"))
                .andExpect(jsonPath("$[1].firstName").value("Sara"))
                .andExpect(jsonPath("$[1].lastName").value("ben"))
                .andExpect(jsonPath("$[1].namePosology").value("Aznol : 200mg"))
                .andExpect(jsonPath("$[1].birthDate").value("18/11/1997"))
                .andReturn();

        verify(medicalrecordService, times(1)).getAll();

    }

    @Test
    void testDeleteMedicalrecord() throws Exception {
        Medicalrecord medicalrecord = new Medicalrecord();
        medicalrecord.setFirstName("John");
        medicalrecord.setLastName("Doe");
        medicalrecord.setNamePosology("Doliprane : 200mg");

        List<Medicalrecord> medicalrecordList = new ArrayList<>();
        medicalrecordList.add(medicalrecord);

        doNothing().when(medicalrecordService).deleteMeicalrecordByFirstNameLastNameAndNamePosology(medicalrecord.getFirstName(), medicalrecord.getLastName(), medicalrecord.getNamePosology());

        mockMvc.perform(MockMvcRequestBuilders.delete("/medicalrecord/"+ medicalrecord.getFirstName() +"/" + medicalrecord.getLastName())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medicalrecord)))
                .andExpect(status().isOk());

        verify(medicalrecordService, times(1)).deleteMeicalrecordByFirstNameLastNameAndNamePosology(medicalrecord.getFirstName(), medicalrecord.getLastName(), medicalrecord.getNamePosology());
    }

    @Test
    void testUpdateMedicalrecord() throws Exception {
        String oldNamePosology = "Doliprane : 200mg";

        Medicalrecord existingMedicalrecord = new Medicalrecord();
        existingMedicalrecord.setFirstName("John");
        existingMedicalrecord.setLastName("Doe");
        existingMedicalrecord.setNamePosology(oldNamePosology);

        List<Medicalrecord> existingMedicalrecords = new ArrayList<>();
        existingMedicalrecords.add(existingMedicalrecord);

        Medicalrecord updatedMedicalrecord = new Medicalrecord();
        updatedMedicalrecord.setFirstName("John");
        updatedMedicalrecord.setLastName("Doe");
        updatedMedicalrecord.setNamePosology("Ibuprofen : 400mg");

        List<Medicalrecord> updatedMedicalrecords = new ArrayList<>();
        updatedMedicalrecords.add(updatedMedicalrecord);

        when(medicalrecordService.getMedicalrecorByFirstNameAndLastName(existingMedicalrecord.getFirstName(), existingMedicalrecord.getLastName()))
                .thenReturn((updatedMedicalrecords));

        mockMvc.perform(MockMvcRequestBuilders.put("/medicalrecord" + "/{oldNamePosology}", oldNamePosology)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedMedicalrecord)))
                .andExpect(status().isOk());

        verify(medicalrecordService, times(1)).updateMedicalrecords(updatedMedicalrecord, oldNamePosology);
    }

    @Test
    void testFindByFirstNameLastNameAndPosology() throws Exception {
        String testFirstName = "John";
        String testLastName = "Doe";
        String testNamePosology = "Doliprane : 200mg";

        Medicalrecord medicalrecord = new Medicalrecord();
        medicalrecord.setFirstName(testFirstName);
        medicalrecord.setLastName(testLastName);
        medicalrecord.setNamePosology(testNamePosology);

        when(medicalrecordService.findByFirstNameLastNameAndPosology(testFirstName, testLastName, testNamePosology)).thenReturn(medicalrecord);

        mockMvc.perform(MockMvcRequestBuilders.get("/medicalrecord" + "/findByFirstNameLastNameAndPosology/{firstName}/{lastName}/{namePosology}", testFirstName, testLastName, testNamePosology))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(testFirstName))
                .andExpect(jsonPath("$.lastName").value(testLastName))
                .andExpect(jsonPath("$.namePosology").value(testNamePosology));

        verify(medicalrecordService, times(1)).findByFirstNameLastNameAndPosology(testFirstName, testLastName, testNamePosology);
    }
}



