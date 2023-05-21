package com.safetynet.alerts.service;
import com.safetynet.alerts.model.Firestation;
import java.util.List;

public interface FirestationService {

    public void add(Firestation firestation);

    public List<Firestation> getAll();

    public Firestation getFirestationByAddress(String address);

    public void deleteFirestationByAddress(String address);

    public void updateFirestation(Firestation updateFirestation);

    public List<String> getFirestationAddressByStation(String stationNumber);
}
