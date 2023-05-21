package com.safetynet.alerts.controller;
import com.safetynet.alerts.model.Medicalrecord;
import com.safetynet.alerts.service.impl.MedicalrecordServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medicalrecord")
@Slf4j
public class MedicalrecordController {

    @Autowired
    MedicalrecordServiceImpl medicalrecordServiceImpl;

    @PostMapping
    public void addMedicalrecord(@RequestBody Medicalrecord medicalrecord) {
        log.info("Creation de la personne {}", medicalrecord);

        medicalrecordServiceImpl.add(medicalrecord);
    }

    @GetMapping
    public List<Medicalrecord> getAll() {
        log.info("chercher tous les dossiers medicales ");
        return medicalrecordServiceImpl.getAll();
    }

    @DeleteMapping("/{firstName}/{lastName}")
    public void deleteMedicalrecord(@RequestBody Medicalrecord medicalrecord, @PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        log.info("Supprimer un dossier medicale par prenom = {} && par nom ={}", firstName, lastName);
        medicalrecordServiceImpl.deleteMeicalrecordByFirstNameLastNameAndNamePosology(firstName, lastName, medicalrecord.getNamePosology());
    }

    @PutMapping("/{oldNamePosology}")
    public ResponseEntity<String> updateMedicalrecord(@RequestBody Medicalrecord updateMedicalrecord, @PathVariable("oldNamePosology") String oldNamePosology) {
        log.info("Mise à jour d'un dossier medical par lancien = {}", oldNamePosology);

        List<Medicalrecord> existingMedicalrecord = medicalrecordServiceImpl.getMedicalrecorByFirstNameAndLastName(updateMedicalrecord.getFirstName(), updateMedicalrecord.getLastName());
        if (existingMedicalrecord == null) {
            return new ResponseEntity<>("Medicalrecord not found", HttpStatus.NOT_FOUND);
        }
        medicalrecordServiceImpl.updateMedicalrecords(updateMedicalrecord, oldNamePosology);

        return new ResponseEntity<>("Medicalrecord updated successfully", HttpStatus.OK);

    }

    @GetMapping("/findByFirstNameLastNameAndPosology/{firstName}/{lastName}/{namePosology}")
    public Medicalrecord findByFirstNameLastNameAndPosology(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName, @PathVariable("namePosology") String namePosology) {
        log.info("Chercher des dossiers medicales par prénom = {} && par nom = {} ", firstName, lastName);
        return medicalrecordServiceImpl.findByFirstNameLastNameAndPosology(firstName, lastName, namePosology);
    }
}

