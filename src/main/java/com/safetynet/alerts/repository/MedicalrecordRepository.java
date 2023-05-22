package com.safetynet.alerts.repository;
import com.safetynet.alerts.model.Medicalrecord;
import java.util.List;
public interface MedicalrecordRepository {


    public void save(Medicalrecord medicalrecord);

    public Medicalrecord findByFirstNameLastNameAndPosology(String firstName, String lastName, String namePosology);

    public List<Medicalrecord> getAll();

    public List<Medicalrecord> getMedicalrecorByFirstNameAndLastName(String firstName, String lastName);


    public void deleteMedicalrecordByFirstNameLastNameAndPosology(String firstName, String lastName, String namePosology);

    public void updateMedicalrecords(Medicalrecord updateMedicalrecord, String oldNamePosology);

}
