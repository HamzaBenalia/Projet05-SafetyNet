package com.safetynet.alerts.repository;
import com.safetynet.alerts.model.Firestation;
import java.util.List;

public interface FirestationRepository {

    void save(Firestation firestation);

    List<Firestation> getAll();

    void deleteFirestation(Firestation firestation);

    void updateFirestation(Firestation updateFirestation);

    List<String> getAdresseByStation(String stationNumber);

    List<Firestation> getFirestationByStation(String stationNumber);

    List<String> getStationByAddress(String address);
}
