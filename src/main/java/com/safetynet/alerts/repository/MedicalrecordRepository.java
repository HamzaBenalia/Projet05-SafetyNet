package com.safetynet.alerts.repository;
import com.safetynet.alerts.model.Medicalrecord;
import java.util.List;

public interface MedicalrecordRepository {


    void save(Medicalrecord medicalrecord);

    Medicalrecord findByFirstNameLastNameAndPosology(String firstName, String lastName, String namePosology);

    List<Medicalrecord> getAll();

    List<Medicalrecord> getMedicalrecorByFirstNameAndLastName(String firstName, String lastName);


    void deleteMedicalrecordByFirstNameLastNameAndPosology(String firstName, String lastName, String namePosology);

    void updateMedicalrecords(Medicalrecord updateMedicalrecord, String oldNamePosology);

}
