package com.safetynet.alerts.service;
import com.safetynet.alerts.dto.CommunityEmail;
import java.util.List;
public interface CommunityEmailService {

    public void addAll(CommunityEmail communityEmail);

    public List<CommunityEmail> getCommunity();

    public List<String> getEmailsByCity(String city);

    public List<String> getAllEmails();

    public List<String> getAllCitys();

    }