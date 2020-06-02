package utn.edu.tpfinal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.edu.tpfinal.models.Call;
import utn.edu.tpfinal.models.PhoneLine;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CallsByUserOnDayX {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private String username;
    @JsonProperty
    private List<CallsByDayDTO> calls;
}

