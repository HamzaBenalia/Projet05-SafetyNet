package com.safetynet.alerts.dto.fire;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetynet.alerts.model.Allergy;
import com.safetynet.alerts.model.Medicalrecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FireDto {
    @JsonProperty("fire")
    private FireDataDto fireDataDto;
    private List<Allergy> allergies;
    private List<Medicalrecord> medicalrecords;

}
