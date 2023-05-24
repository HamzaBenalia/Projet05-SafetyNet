package com.safetynet.alerts.service.impl;
import com.safetynet.alerts.model.Medicalrecord;
import com.safetynet.alerts.repository.MedicalrecordRepository;
import com.safetynet.alerts.service.MedicalrecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicalrecordServiceImpl implements MedicalrecordService {

    @Autowired
    private MedicalrecordRepository medicalrecordRepository;

    public void add(Medicalrecord medicalrecord) {
        if (medicalrecordRepository.findByFirstNameLastNameAndPosology(medicalrecord.getFirstName(), medicalrecord.getLastName(), medicalrecord.getNamePosology()) != null) {
            return;
        }
        medicalrecordRepository.save(medicalrecord);
    }

    public List<Medicalrecord> getAll() {
        return medicalrecordRepository.getAll();
    }

    public List<Medicalrecord> getMedicalrecorByFirstNameAndLastName(String firstName, String lastName) {
        return medicalrecordRepository.getMedicalrecorByFirstNameAndLastName(firstName, lastName);
    }


    public void deleteMeicalrecordByFirstNameLastNameAndNamePosology(String fistName, String lastName, String namePosology) {
        medicalrecordRepository.deleteMedicalrecordByFirstNameLastNameAndPosology(fistName, lastName, namePosology);
    }

    public void updateMedicalrecords(Medicalrecord updateMedicalrecord, String oldNamePosology) {
        medicalrecordRepository.updateMedicalrecords(updateMedicalrecord, oldNamePosology);
    }

    public Medicalrecord findByFirstNameLastNameAndPosology(String firstName, String lastName, String namePosology) {
        return medicalrecordRepository.findByFirstNameLastNameAndPosology(firstName, lastName, namePosology);
    }
}
