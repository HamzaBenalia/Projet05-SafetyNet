package com.safetynet.alerts.service;
import com.safetynet.alerts.model.Allergy;
import java.util.List;

public interface AllergyService {

    public void add(Allergy allergy);

    public void deleteMeicalrecordByFirstNameLastNameAndNamePosology(String fistName, String lastName, String nameAllergy);

    public void updateAllergy(Allergy updateAllergy, String oldNameAllergy);

    public List<Allergy> getAll();

}
