package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.CommunityEmail;
import com.safetynet.alerts.service.CommunityEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/communityEmail")
public class CommunityEmailController {

    @Autowired
    private CommunityEmailService communityEmailService;

    // Endpoint pour obtenir les adresses email de tous les habitants

    @PostMapping
    public void saveCommunityEmail(@RequestBody CommunityEmail communityEmail) {
        communityEmailService.addAll(communityEmail);
    }


    @GetMapping("/allemails")
    public List<String> getAllEmails() {
        return communityEmailService.getAllEmails();
    }

    @GetMapping("/allcitys")
    public List<String> getAllCitys() {
        return communityEmailService.getAllCitys();
    }

    @GetMapping
    public List<String> getEmailsByCity(@RequestParam String city) {
        return communityEmailService.getEmailsByCity(city);
    }
}



