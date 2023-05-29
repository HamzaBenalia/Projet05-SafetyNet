package com.safetynet.alerts.controller;
import com.safetynet.alerts.model.Allergy;
import com.safetynet.alerts.model.Medicalrecord;
import com.safetynet.alerts.service.AllergyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/allergy")
@Slf4j
public class AllergyController {

    @Autowired
    private AllergyService allergyService;

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

    @PutMapping("/{nameAllergy}")
    public ResponseEntity<String> updateMedicalrecord(@RequestBody Allergy updatedAllergy, @PathVariable("nameAllergy") String nameAllergy) {
        log.info("Mise à jour d'un dossier medical par  = {}",nameAllergy);

        Allergy existingAllergy = allergyService.getMedicalrecorByFirstNameAndLastName(updatedAllergy.getFirstName(),updatedAllergy.getLastName(),nameAllergy);
        if (existingAllergy == null) {
            return new ResponseEntity<>("Medicalrecord not found", HttpStatus.NOT_FOUND);
        }
        allergyService.updateAllergy(updatedAllergy, nameAllergy );

        return new ResponseEntity<>("Medicalrecord updated successfully", HttpStatus.OK);
    }
}
