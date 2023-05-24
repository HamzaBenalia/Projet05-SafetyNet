package com.safetynet.alerts.dto.childAlert;
import com.safetynet.alerts.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildDto {
    private List<Person> adults;
    private List<ChildDataDto> children;
}
