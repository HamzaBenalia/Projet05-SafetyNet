package com.safetynet.alerts.service;
import com.safetynet.alerts.model.Allergy;
import java.util.List;

public interface AllergyService {

    void add(Allergy allergy);

    Allergy getMedicalrecorByFirstNameAndLastName(String firstName, String lastName, String allergy);

    void deleteMeicalrecordByFirstNameLastNameAndNamePosology(String fistName, String lastName, String nameAllergy);

    void updateAllergy(Allergy updateAllergy, String oldNameAllergy);

    List<Allergy> getAll();

}
