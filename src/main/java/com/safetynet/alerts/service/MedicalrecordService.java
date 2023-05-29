package com.safetynet.alerts.service;
import com.safetynet.alerts.model.Medicalrecord;
import java.util.List;

public interface MedicalrecordService {


    void add(Medicalrecord medicalrecord);

    List<Medicalrecord> getAll();

    List<Medicalrecord> getMedicalrecorByFirstNameAndLastName(String firstName, String lastName);


    void deleteMeicalrecordByFirstNameLastNameAndNamePosology(String fistName, String lastName, String namePosology);

    void updateMedicalrecords(Medicalrecord updateMedicalrecord, String oldNamePosology);

    Medicalrecord findByFirstNameLastNameAndPosology(String firstName, String lastName, String namePosology);
}
