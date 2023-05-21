package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.childAlert.ChildDto;
import com.safetynet.alerts.dto.fire.FireDto;
import com.safetynet.alerts.dto.flood.FloodDto;
import com.safetynet.alerts.dto.personInfo.PersonInfoDto;
import com.safetynet.alerts.dto.phoneAlert.PhoneAlertDto;
import com.safetynet.alerts.dto.stationDto.StationInfoDto;
import com.safetynet.alerts.model.Person;

import java.util.List;
import java.util.Map;

public interface PersonService {

    void add(Person person);

    List<Person> getAll();

    Person getPersonByFirstNameAndLastName(String firstName, String lastName);

    void deleteByFirstNameAndLastName(String firstName, String lastName);

    void updatePerson(Person updatePerson);

    List<PersonInfoDto> getPersonInfos(String firstName, String lastName);

    List<Person> getPersonByphone(String phone);

    StationInfoDto getPersonByFirestation(String firestationNumber);

    PhoneAlertDto getPhonesByFirestation(String firestationNumber);

    List<FireDto> getResidentsByAddress(String address);

    Map<String, List<FloodDto>> getResidentsByStationNumber(List<String> stations);

    ChildDto getChildrenByAddress(String address);
}


