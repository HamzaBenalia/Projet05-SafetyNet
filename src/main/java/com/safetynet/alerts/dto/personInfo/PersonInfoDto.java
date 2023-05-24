package com.safetynet.alerts.dto.personInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetynet.alerts.model.Allergy;
import com.safetynet.alerts.model.Medicalrecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoDto {
    @JsonProperty("person")
    private PersonDataDto personDataDto;
    private List<Allergy> allergies;
    private List<Medicalrecord> medicalrecords;
}
