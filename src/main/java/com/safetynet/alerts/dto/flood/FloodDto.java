package com.safetynet.alerts.dto.flood;
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
public class FloodDto {
    @JsonProperty("flood")
    private FloodDataDto floodDataDto;
    private List<Allergy> allergies;
    private List<Medicalrecord> medicalrecords;
}
