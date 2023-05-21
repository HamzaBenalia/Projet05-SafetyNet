package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Allergy;
import com.safetynet.alerts.repository.AllergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllergyService {

    @Autowired
    AllergyRepository allergyRepository;

    public void add(Allergy allergy) {
        if (allergyRepository.findByFirstNameLastNameAndAllergy(allergy.getFirstName(), allergy.getLastName(), allergy.getNameAllergy()) != null) {
            return;
        }
        allergyRepository.save(allergy);
    }

    public void deleteMeicalrecordByFirstNameLastNameAndNamePosology(String fistName, String lastName, String nameAllergy) {
        allergyRepository.deleteAllergyByFirstNameLastNameAndAllergy(fistName, lastName, nameAllergy);
    }

    public void updateAllergy(Allergy updateAllergy, String oldNameAllergy) {
        allergyRepository.updateAllergy(updateAllergy, oldNameAllergy);
    }

    public List<Allergy> getAll() {
        return allergyRepository.getAll();
    }

}
