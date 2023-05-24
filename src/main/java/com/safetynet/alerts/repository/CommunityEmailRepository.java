package com.safetynet.alerts.repository;
import com.safetynet.alerts.dto.CommunityEmail;
import java.util.List;

public interface CommunityEmailRepository {

    public void saveEmailCommunity(CommunityEmail communityEmail);

    public List<CommunityEmail> getAllCommunity();

    public List<String> getEmails();

    public List<String> getCities();

    public List<String> findEmailByCity(String city);
}
