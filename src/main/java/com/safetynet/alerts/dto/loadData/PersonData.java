package com.safetynet.alerts.dto.loadData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonData {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String city;
    private String zip;
    private String phone;
}
