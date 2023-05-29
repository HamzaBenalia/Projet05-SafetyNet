package com.safetynet.alerts.repository.impl;
import com.safetynet.alerts.model.Allergy;
import com.safetynet.alerts.repository.AllergyRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AllergyRepositoryImpl implements AllergyRepository {

    private final List<Allergy> allergies = new ArrayList<>();

    @Override
    public void save(Allergy allergy) {
        allergies.add(allergy);
    }

    @Override
    public Allergy findByFirstNameLastNameAndAllergy(String firstName, String lastName, String nameAllergy) {
        for (Allergy allergy : allergies) {
            if (allergy.getFirstName().equals(firstName) && allergy.getLastName().equals(lastName) && allergy.getNameAllergy().equals(nameAllergy)) {
                return allergy;
            }
        }
        return null;
    }

    @Override
    public List<Allergy> getAll() {
        return allergies;
    }

    @Override
    public List<Allergy> deleteAllergyByFirstNameLastNameAndAllergy(String firstName, String lastName, String nameAllergy) {
        Allergy allergy = findByFirstNameLastNameAndAllergy(firstName, lastName, nameAllergy);
        if (allergy != null) {
            allergies.remove(allergy);
        }
        return allergies;
    }

    @Override
    public void updateAllergy(Allergy updateAllergy, String oldNameAllergy) {
        Allergy oldAllergy = findByFirstNameLastNameAndAllergy(updateAllergy.getFirstName(), updateAllergy.getLastName(), oldNameAllergy);
        if (oldAllergy != null) {
            allergies.remove(oldAllergy);
            allergies.add(updateAllergy);
        }

    }

    @Override
    public List<Allergy> findByFirstNameAndLastName(String firstName, String lastName) {
        List<Allergy> allergieList = new ArrayList<>();
        for (Allergy allergy : allergies) {
            if (allergy.getFirstName().equals(firstName) && allergy.getLastName().equals(lastName)) {
                allergieList.add(allergy);
            }
        }
        return allergieList;
    }

}
