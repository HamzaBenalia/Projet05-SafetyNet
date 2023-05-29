package com.safetynet.alerts.dto.loadData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitData {
    private List<PersonData> persons;
    private List<FirestationData> firestations;
    private List<MedicalRecordData> medicalrecords;
}
