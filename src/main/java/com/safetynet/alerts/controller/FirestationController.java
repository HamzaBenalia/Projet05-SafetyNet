package com.safetynet.alerts.controller;
import com.safetynet.alerts.dto.stationDto.StationInfoDto;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.service.impl.FirestationServiceImpl;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class FirestationController {
    public static final String PATH = "/firestation";

    @Autowired
    private FirestationServiceImpl firestationServiceImpl;

    @Autowired
    private PersonService personService;

    @PostMapping(PATH)
    public void addFirestation(@RequestBody Firestation firestation) {
        log.info("Creation de la personne {}", firestation);
        firestationServiceImpl.add(firestation);
    }

    @GetMapping(PATH + "/all")
    public List<Firestation> getAllFirestation() {
        log.info("Recherche de toutes les stations");
        return firestationServiceImpl.getAll();
    }

    @DeleteMapping(PATH + "/{address}")
    public void deleteFirestation(@PathVariable("address") String firestation) {
        log.info("Supprimer une personne par firestation = {}", firestation);
        firestationServiceImpl.deleteFirestationByAddress(firestation);
    }

    @PutMapping(PATH)
    public ResponseEntity<String> updateFirestation(@RequestBody Firestation updateFirestation) {
        log.info("Mise à jour avec succée de = {}", updateFirestation);
        // Vérifier si la personne à mettre à jour existe
        Firestation existingFirestation = firestationServiceImpl.getFirestationByAddress(updateFirestation.getAddress());
        if (existingFirestation == null) {
            return new ResponseEntity<>("Firestation not found", HttpStatus.NOT_FOUND);
        }
        firestationServiceImpl.updateFirestation(updateFirestation);
        return new ResponseEntity<>("Firestation updated successfully", HttpStatus.OK);
    }

    @GetMapping(PATH)
    public StationInfoDto getPersonByFirestationNumber(@RequestParam("stationNumber") String stationNumber) {
        log.info("Chercher les personnes couvertes par la caserne de pompiers par = {}", stationNumber);
        return personService.getPersonByFirestation(stationNumber);
    }

}



