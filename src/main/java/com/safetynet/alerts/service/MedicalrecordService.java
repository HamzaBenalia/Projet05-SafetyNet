package com.safetynet.alerts.service;
import com.safetynet.alerts.model.Medicalrecord;
import java.util.List;

public interface MedicalrecordService {


    public void add(Medicalrecord medicalrecord);

    public List<Medicalrecord> getAll();

    public List<Medicalrecord> getMedicalrecorByFirstNameAndLastName(String firstName, String lastName);


    public void deleteMeicalrecordByFirstNameLastNameAndNamePosology(String fistName, String lastName, String namePosology);

    public void updateMedicalrecords(Medicalrecord updateMedicalrecord, String oldNamePosology);

    public Medicalrecord findByFirstNameLastNameAndPosology(String firstName, String lastName, String namePosology);
}
