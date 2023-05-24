package com.safetynet.alerts.service.impl;
import com.safetynet.alerts.dto.CommunityEmail;
import com.safetynet.alerts.repository.impl.CommunityEmailRepositoryImpl;
import com.safetynet.alerts.service.CommunityEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommunityEmailServiceImpl implements CommunityEmailService {

    @Autowired
    private CommunityEmailRepositoryImpl communityEmailRepository;

    public void addAll(CommunityEmail communityEmail) {
        communityEmailRepository.saveEmailCommunity(communityEmail);
    }

    public List<CommunityEmail> getCommunity() {
        return communityEmailRepository.getAllCommunity();
    }

    public List<String> getEmailsByCity(String city) {
        return communityEmailRepository.findEmailByCity(city);
    }

    public List<String> getAllEmails() {
        return communityEmailRepository.getEmails();
    }

    public List<String> getAllCitys() {
        return communityEmailRepository.getCities();
    }
}


