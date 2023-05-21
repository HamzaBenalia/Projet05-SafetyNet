package com.safetynet.alerts.service;
import com.safetynet.alerts.dto.CommunityEmail;
import com.safetynet.alerts.repository.CommunityEmailRepository;
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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommunityEmailServiceTest {


    @InjectMocks
    CommunityEmailService communityEmailService;

    @Mock
    CommunityEmailRepository communityEmailRepository;

    @Captor
    private ArgumentCaptor<CommunityEmail> communityEmailArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> cityArgumentCaptor;

    @Test
    public void addAllCommunityEmailTest() {

        CommunityEmail communityEmail = new CommunityEmail("Hamza", "Ben", "Hamza@gmail.com", "Toulouse");

        communityEmailService.addAll(communityEmail);

        Mockito.verify(communityEmailRepository, times(1)).saveEmailCommunity(communityEmailArgumentCaptor.capture());

        assertThat(communityEmailArgumentCaptor.getValue())
                .extracting("firstName", "lastName", "city", "email")
                .containsExactly(communityEmail.getFirstName(), communityEmail.getLastName(), communityEmail.getCity(), communityEmail.getEmail());

    }

    @Test
    public void testGetAllCommunity() {

        // Create some persons
        CommunityEmail communityEmailDoe = new CommunityEmail("John", "Doe", "john.doe@example.com", "Toulouse");
        CommunityEmail communityEmailJane = new CommunityEmail("Jane", "Doe", "jane.doe@example.com", "Montpelllier");
        List<CommunityEmail> allCommunityEmail = Arrays.asList(communityEmailDoe, communityEmailJane);

        when(communityEmailRepository.getAllCommunity()).thenReturn(allCommunityEmail);

        List<CommunityEmail> result = communityEmailService.getCommunity();
        Mockito.verify(communityEmailRepository, times(1)).getAllCommunity();
        assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(communityEmailDoe));
        Assertions.assertTrue(result.contains(communityEmailJane));
    }

    @Test
    public void testGetEmails() {

        CommunityEmail communityEmailHamza = new CommunityEmail("Hamza", "Benalia", "Hamza@gmail.com", "Toulouse");
        CommunityEmail communityEmailSara = new CommunityEmail("Sara", "Ben", "Sara@gmail.com", "Toulouse");

        List<CommunityEmail> communityEmails = new ArrayList<>();
        communityEmails.add(communityEmailHamza);
        communityEmails.add(communityEmailSara);
        //List<String> strings = new ArrayList<>();
        List<String> allEmails = new ArrayList<>();

        when(communityEmailService.getAllEmails()).thenReturn(allEmails);

        List<String> emails = communityEmailRepository.getEmails();

        String result = "Hamza@gmail.com";

        Assertions.assertNotNull(emails);


    }

    @Test
    public void testGetAllCities() {
        CommunityEmail communityEmailHamza = new CommunityEmail("Hamza", "Benalia", "Hamza@gmail.com", "Toulouse");
        communityEmailRepository.saveEmailCommunity(communityEmailHamza);


        List<String> results = communityEmailService.getAllCitys();

        Assertions.assertNotNull(results);
        verify(communityEmailRepository, times(1)).getCitys();
    }


    @Test
    public void testGetEmailsByCity() {
        String city = "Toulouse";
        String mail = "hamza.benalia@gmail.com";

        when(communityEmailRepository.findEmailByCity(anyString())).thenReturn(List.of(mail));

        List<String> results = communityEmailService.getEmailsByCity(city);

        verify(communityEmailRepository, times(1)).findEmailByCity(cityArgumentCaptor.capture());
        Assertions.assertNotNull(results);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(mail, results.get(0));

    }

}
