package utn.edu.tpfinal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.edu.tpfinal.models.PhoneLine;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserResponseDTO {
    @JsonProperty
    private Integer dni;
    @JsonProperty
    private String username;
    @JsonProperty
    private String name;
    @JsonProperty
    private String surname;
    @JsonProperty
    private List<PhoneLine> phoneLines;
    @JsonProperty
    private List<BillForUserDTO> bills;
}
