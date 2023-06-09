package com.safetynet.alerts.repository.impl;
import com.safetynet.alerts.model.Medicalrecord;
import com.safetynet.alerts.repository.MedicalrecordRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MedicalrecordRepositoryImpl implements MedicalrecordRepository {


    private  List<Medicalrecord> medicalrecords = new ArrayList<>();

    public void save(Medicalrecord medicalrecord) {
        medicalrecords.add(medicalrecord);
    }

    public Medicalrecord findByFirstNameLastNameAndPosology(String firstName, String lastName, String namePosology) {
        for (Medicalrecord medicalrecord : medicalrecords) {
            if (medicalrecord.getFirstName().equals(firstName) && medicalrecord.getLastName().equals(lastName) && medicalrecord.getNamePosology().equals(namePosology)) {
                return medicalrecord;
            }
        }
        return null;
    }

    public List<Medicalrecord> getAll() {
        return medicalrecords;
    }

    public List<Medicalrecord> getMedicalrecorByFirstNameAndLastName(String firstName, String lastName) {
        List<Medicalrecord> medicalrecordList = new ArrayList<>();
        for (Medicalrecord medicalrecord : medicalrecords) {
            if (medicalrecord.getFirstName().equals(firstName) && medicalrecord.getLastName().equals(lastName)) {
                medicalrecordList.add(medicalrecord);
            }
        }

        return medicalrecordList;
    }


    public void deleteMedicalrecordByFirstNameLastNameAndPosology(String firstName, String lastName, String namePosology) {
        Medicalrecord medicalrecord = findByFirstNameLastNameAndPosology(firstName, lastName, namePosology);
        if (medicalrecord != null) {
            medicalrecords.remove(medicalrecord);
        }
    }

    public void updateMedicalrecords(Medicalrecord updateMedicalrecord, String oldNamePosology) {
        Medicalrecord oldMedicalRecord = findByFirstNameLastNameAndPosology(updateMedicalrecord.getFirstName(), updateMedicalrecord.getLastName(), oldNamePosology);
        if (oldMedicalRecord != null) {
            medicalrecords.remove(oldMedicalRecord);
            medicalrecords.add(updateMedicalrecord);
        }
    }

}
