package com.safetynet.alerts.dto;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import lombok.Data;
import java.util.List;

@Data
public class FirestationAlert {

    private Person person;
    private FirestationAlert firestationAlert;

    public FirestationAlert(Person person, List<Firestation> firestationList) {
    }
}
