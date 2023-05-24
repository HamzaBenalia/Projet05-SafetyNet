package com.safetynet.alerts.controller;
import com.safetynet.alerts.model.Allergy;
import com.safetynet.alerts.service.AllergyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/allergy")
@Slf4j
public class AllergyController {

    @Autowired
    AllergyService allergyService;

    @PostMapping
    public void addAllergy(@RequestBody Allergy allergy) {
        log.info("Creation d'allergies {}", allergy);
        allergyService.add(allergy);
    }

    @GetMapping
    public List<Allergy> getAll() {
        log.info("Chercher la liste d'allergies");
        return allergyService.getAll();
    }
}
