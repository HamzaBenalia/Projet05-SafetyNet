package com.safetynet.alerts.service.impl;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Iterator;
import java.util.List;

@Service
public class FirestationServiceImpl implements FirestationService {

    @Autowired
    private FirestationRepository firestationRepository;

    public void add(Firestation firestation) {
        if (getFirestationByAddress(firestation.getAddress()) != null) {
            return;
        }
        firestationRepository.save(firestation);

    }

    public List<Firestation> getAll() {
        return firestationRepository.getAll();
    }

    public Firestation getFirestationByAddress(String address) {
        List<Firestation> firestations = firestationRepository.getAll();
        for (Firestation firestation : firestations) {
            if (firestation.getAddress().equals(address)) {
                return firestation;
            }
        }

        return null;
    }

    public void deleteFirestationByAddress(String address) {
        List<Firestation> firestations = firestationRepository.getAll();
        Iterator<Firestation> iterator = firestations.iterator();
        while (iterator.hasNext()) {
            Firestation firestation = iterator.next();
            if (firestation.getAddress().equals(address)) {
                iterator.remove();
            }
        }
    }

    public void updateFirestation(Firestation updateFirestation) {
        firestationRepository.updateFirestation(updateFirestation);
    }

    public List<String> getFirestationAddressByStation(String stationNumber) {
        return firestationRepository.getAdresseByStation(stationNumber);
    }

}
