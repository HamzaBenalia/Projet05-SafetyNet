package com.safetynet.alerts.controller;
import com.safetynet.alerts.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/communityEmail")
public class CommunityEmailController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<String> getEmailsByCity(@RequestParam("city") String city) {
        log.info("Chercher les mails des personnes par city {}", city);
        return personService.getEmailsByCity(city);
    }
}



