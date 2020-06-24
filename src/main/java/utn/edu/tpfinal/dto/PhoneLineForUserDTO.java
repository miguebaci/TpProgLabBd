package utn.edu.tpfinal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PhoneLineForUserDTO {
    @JsonProperty
    private String lineNumber;

    @JsonProperty
    private String lineType;
}
