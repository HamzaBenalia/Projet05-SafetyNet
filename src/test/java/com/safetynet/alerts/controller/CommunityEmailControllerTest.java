package com.safetynet.alerts.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.service.DataPopulatorService;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@AutoConfigureMockMvc
public class CommunityEmailControllerTest {

    @MockBean
    PersonService personService;
    @MockBean
    private DataPopulatorService dataPopulatorService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void getEmailsByCityTest() throws Exception {
        String city = "Toulouse";
        List<String> emails = Arrays.asList("hamza@gmail.com", "john@gmail.com");

        when(personService.getEmailsByCity(city)).thenReturn(emails);

        mockMvc.perform(MockMvcRequestBuilders.get("/communityEmail")
                        .param("city", city))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]").value("hamza@gmail.com"))
                .andExpect(jsonPath("$[1]").value("john@gmail.com"));

        verify(personService, times(1)).getEmailsByCity(city);
    }
}
