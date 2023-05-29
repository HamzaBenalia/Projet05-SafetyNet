package com.safetynet.alerts.repository;
import com.safetynet.alerts.model.Allergy;
import com.safetynet.alerts.repository.impl.AllergyRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

public class AllergyRepositoryTest {

    @Test
    public void testSave() {
        Allergy allergy = new Allergy("John", "Boyd", "Peanuts");
        AllergyRepositoryImpl allergyRepository = new AllergyRepositoryImpl();
        allergyRepository.save(allergy);

        Assertions.assertTrue(allergyRepository.getAll().contains(allergy));
    }


    @Test
    public void testFindByFirstNameLastNameAndAllergy() {
        AllergyRepositoryImpl allergyRepository = new AllergyRepositoryImpl();
        allergyRepository.save(new Allergy("John", "Doe", "Peanuts"));
        allergyRepository.save(new Allergy("Jane", "Doe", "Gluten"));

        Allergy foundAllergy = allergyRepository.findByFirstNameLastNameAndAllergy("John", "Doe", "Peanuts");
        Assertions.assertNotNull(foundAllergy);
        Assertions.assertEquals("John", foundAllergy.getFirstName());
        Assertions.assertEquals("Doe", foundAllergy.getLastName());
        Assertions.assertEquals("Peanuts", foundAllergy.getNameAllergy());

        foundAllergy = allergyRepository.findByFirstNameLastNameAndAllergy("Bob", "Smith", "Eggs");
        Assertions.assertNull(foundAllergy);
    }

    @Test
    public void testGetAll() {
        AllergyRepositoryImpl allergyDAO = new AllergyRepositoryImpl();
        allergyDAO.save(new Allergy("John", "Doe", "Peanuts"));
        allergyDAO.save(new Allergy("Jane", "Doe", "Gluten"));

        List<Allergy> allAllergies = allergyDAO.getAll();
        Assertions.assertEquals(2, allAllergies.size());
    }

    @Test
    public void testDeleteAllergyByFirstNameLastNameAndAllergy() {
        AllergyRepositoryImpl allergyDAO = new AllergyRepositoryImpl();
        allergyDAO.save(new Allergy("John", "Doe", "Peanuts"));
        allergyDAO.save(new Allergy("Jane", "Doe", "Gluten"));

        List<Allergy> updatedAllergies = allergyDAO.deleteAllergyByFirstNameLastNameAndAllergy("John", "Doe", "Peanuts");
        Assertions.assertEquals(1, updatedAllergies.size());
        Assertions.assertNull(allergyDAO.findByFirstNameLastNameAndAllergy("John", "Doe", "Peanuts"));

        updatedAllergies = allergyDAO.deleteAllergyByFirstNameLastNameAndAllergy("Bob", "Smith", "Eggs");
        Assertions.assertEquals(1, updatedAllergies.size());
    }

    @Test
    public void testUpdateAllergy() {
        AllergyRepositoryImpl allergyDAO = new AllergyRepositoryImpl();
        allergyDAO.save(new Allergy("John", "Doe", "Peanuts"));
        allergyDAO.save(new Allergy("Jane", "Doe", "Gluten"));

        Allergy updatedAllergy = new Allergy("John", "Doe", "Shellfish");
        allergyDAO.updateAllergy(updatedAllergy, "Peanuts");
        Assertions.assertEquals("Shellfish", allergyDAO.findByFirstNameLastNameAndAllergy("John", "Doe", "Shellfish").getNameAllergy());
        Assertions.assertNull(allergyDAO.findByFirstNameLastNameAndAllergy("John", "Doe", "Peanuts"));

        updatedAllergy = new Allergy("Bob", "Smith", "Eggs");
        allergyDAO.updateAllergy(updatedAllergy, "Peanuts");
        Assertions.assertNull(allergyDAO.findByFirstNameLastNameAndAllergy("Bob", "Smith", "Eggs"));
    }

    @Test
    public void testFindByFirstNameAndLastName() {
        AllergyRepositoryImpl allergyDAO = new AllergyRepositoryImpl();
        allergyDAO.save(new Allergy("John", "Doe", "Peanuts"));
        allergyDAO.save(new Allergy("Jane", "Doe", "Gluten"));
        allergyDAO.save(new Allergy("John", "Doe", "Shellfish"));

        List<Allergy> johnDoeAllergies = allergyDAO.findByFirstNameAndLastName("John", "Doe");
        Assertions.assertEquals(2, johnDoeAllergies.size());
        Assertions.assertTrue(johnDoeAllergies.stream().anyMatch(allergy -> allergy.getNameAllergy().equals("Peanuts")));
        Assertions.assertTrue(johnDoeAllergies.stream().anyMatch(allergy -> allergy.getNameAllergy().equals("Shellfish")));

        List<Allergy> bobSmithAllergies = allergyDAO.findByFirstNameAndLastName("Bob", "Smith");
        Assertions.assertTrue(bobSmithAllergies.isEmpty());
    }

}





