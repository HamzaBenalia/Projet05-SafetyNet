package com.safetynet.alerts.service;
import com.safetynet.alerts.model.Firestation;
import java.util.List;

public interface FirestationService {

    void add(Firestation firestation);

    List<Firestation> getAll();

    Firestation getFirestationByAddress(String address);

    void deleteFirestationByAddress(String address);

    void updateFirestation(Firestation updateFirestation);

    List<String> getFirestationAddressByStation(String stationNumber);
}
