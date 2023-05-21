package com.safetynet.alerts.controller;
import com.safetynet.alerts.dto.CommunityEmail;
import com.safetynet.alerts.service.impl.CommunityEmailServiceImpl;
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
    private CommunityEmailServiceImpl communityEmailServiceImpl;

    // Endpoint pour obtenir les adresses email de tous les habitants

    @PostMapping
    public void saveCommunityEmail(@RequestBody CommunityEmail communityEmail) {
        communityEmailServiceImpl.addAll(communityEmail);
    }


    @GetMapping("/allemails")
    public List<String> getAllEmails() {
        return communityEmailServiceImpl.getAllEmails();
    }

    @GetMapping("/allcitys")
    public List<String> getAllCitys() {
        return communityEmailServiceImpl.getAllCitys();
    }

    @GetMapping
    public List<String> getEmailsByCity(@RequestParam String city) {
        return communityEmailServiceImpl.getEmailsByCity(city);
    }
}



