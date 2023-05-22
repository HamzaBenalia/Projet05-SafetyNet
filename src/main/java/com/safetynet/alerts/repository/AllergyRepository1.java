package com.safetynet.alerts.repository;
import com.safetynet.alerts.model.Allergy;
import java.util.List;

public interface AllergyRepository1 {

    public void save(Allergy allergy);

    public Allergy findByFirstNameLastNameAndAllergy(String firstName, String lastName, String nameAllergy);

    public List<Allergy> getAll();

    public List<Allergy> deleteAllergyByFirstNameLastNameAndAllergy(String firstName, String lastName, String nameAllergy);

    public void updateAllergy(Allergy updateAllergy, String oldNameAllergy);

    public List<Allergy> findByFirstNameAndLastName(String firstName, String lastName);

}
