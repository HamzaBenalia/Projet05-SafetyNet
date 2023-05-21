package com.safetynet.alerts.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.service.impl.DataPopulatorServiceImpl;
import com.safetynet.alerts.service.impl.FirestationServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
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
import static com.safetynet.alerts.controller.FirestationController.PATH;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
public class FirestationControllerTest {

    @MockBean
    private FirestationServiceImpl firestationServiceImpl;
    @MockBean
    private PersonService personService;
    @MockBean
    private DataPopulatorServiceImpl dataPopulatorServiceImpl;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void firestationController_createFirestation_returnCreated() throws Exception {
        Firestation firestation = new Firestation();
        firestation.setAddress("20 rue Toulouse");
        firestation.setStation("1");

        String json = objectMapper.writeValueAsString(firestation);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        Mockito.verify(firestationServiceImpl, times(1)).add(ArgumentMatchers.any(Firestation.class));

    }

    @Test
    public void firestationController_getAllFirestationsTest_returnAllFirestations() throws Exception {

        List<Firestation> firestationList = Arrays.asList(
                new Firestation("12 rue Toulouse", "1"),
                new Firestation("20 rue Toulouse", "2")
        );

        when(firestationServiceImpl.getAll()).thenReturn(firestationList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/firestation/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].address").value("12 rue Toulouse"))
                .andExpect(jsonPath("$[0].station").value("1"))
                .andExpect(jsonPath("$[1].address").value("20 rue Toulouse"))
                .andExpect(jsonPath("$[1].station").value("2"))
                .andReturn();

        Mockito.verify(firestationServiceImpl, times(1)).getAll();


    }

    @Test
    void testDeleteFirestation() throws Exception {
        // Set up test data
        String testAddress = "123 Main St";

        List<Firestation> firestationList = new ArrayList<>();

        Firestation firestation = new Firestation();
        firestation.setStation("1");
        firestation.setAddress("123 Main S");

        firestationList.add(firestation);

        // Mock behavior of firestationService
        doNothing().when(firestationServiceImpl).deleteFirestationByAddress(testAddress);

        // Perform DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete(PATH + "/{123 Main st}", testAddress))
                .andExpect(status().isOk());

        // Verify that the delete method was called in the firestationService
        Mockito.verify(firestationServiceImpl, times(1)).deleteFirestationByAddress(testAddress);


    }

    @Test
    void testUpdateFirestation() throws Exception {
        // Set up test data
        Firestation existingFirestation = new Firestation();
        existingFirestation.setStation("1");
        existingFirestation.setAddress("123 Main St");

        Firestation updatedFirestation = new Firestation();
        updatedFirestation.setStation("2");
        updatedFirestation.setAddress("123 Main St");

        // Mock behavior of firestationService
        when(firestationServiceImpl.getFirestationByAddress(existingFirestation.getAddress())).thenReturn(updatedFirestation);

        // Perform PUT request
        mockMvc.perform(MockMvcRequestBuilders.put(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedFirestation)))
                .andExpect(status().isOk())
                .andExpect(content().string("Firestation updated successfully"));

        // Verify that the update method was called in the firestationService
        Mockito.verify(firestationServiceImpl, times(1)).updateFirestation(updatedFirestation);
    }


    /*@Test
    void testGetPersonByFirestationNumber() throws Exception {

        String testStationNumber = ("1");
        // Set up test data
        PersonStationDto personStationDto = new PersonStationDto();
        personStationDto.setFirstName("Hamza");
        personStationDto.setLastName("Ben");
        personStationDto.setAddress("13 Toulouse");
        personStationDto.setPhone("0766761585");

        List<PersonStationDto> personStationDtos = new ArrayList<>();
        personStationDtos.add(personStationDto);

        StationInfoDto stationInfoDto = new StationInfoDto();
        // Set other properties of the stationInfoDto
        stationInfoDto.setAdult(19);
        stationInfoDto.setMinor(17);
        stationInfoDto.setPersonStationDtos(personStationDtos);

        // Mock behavior of personService
        when(personService.getPersonByFirestation(testStationNumber)).thenReturn(stationInfoDto);

        // Perform GET request
        mockMvc.perform(MockMvcRequestBuilders.get(PATH).param("stationNumber", testStationNumber))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                //.andExpect((ResultMatcher) jsonPath("$.adultCount", is(stationInfoDto.getAdult())))
                //.andExpect((ResultMatcher) jsonPath("$.childCount", is(stationInfoDto.getMinor())))
                .andExpect(jsonPath("$.persons", hasSize(stationInfoDto.getPersonStationDtos().size())));

        // Verify that the getPersonByFirestation method was called in the personService
        Mockito.verify(personService, times(1)).getPersonByFirestation(testStationNumber);
    }
*/
}

