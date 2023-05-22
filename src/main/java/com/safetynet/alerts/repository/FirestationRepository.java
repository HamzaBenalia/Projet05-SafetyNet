package com.safetynet.alerts.repository;
import com.safetynet.alerts.model.Firestation;
import java.util.List;

public interface FirestationRepository {

    public void save(Firestation firestation);

    public List<Firestation> getAll();

    public void deleteFirestation(Firestation firestation);

    public void updateFirestation(Firestation updateFirestation);

    public List<String> getAdresseByStation(String stationNumber);

    public List<Firestation> getFirestationByStation(String stationNumber);

    public List<String> getStationByAddress(String address);
}
