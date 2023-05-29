package com.safetynet.alerts.repository;
import com.safetynet.alerts.model.Allergy;
import java.util.List;

public interface AllergyRepository {

    void save(Allergy allergy);

    Allergy findByFirstNameLastNameAndAllergy(String firstName, String lastName, String nameAllergy);

    List<Allergy> getAll();

    List<Allergy> deleteAllergyByFirstNameLastNameAndAllergy(String firstName, String lastName, String nameAllergy);

    void updateAllergy(Allergy updateAllergy, String oldNameAllergy);

    List<Allergy> findByFirstNameAndLastName(String firstName, String lastName);

}
