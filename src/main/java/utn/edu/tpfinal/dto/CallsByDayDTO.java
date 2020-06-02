package utn.edu.tpfinal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CallsByDayDTO {

    @JsonProperty
    private Integer id_call;
    @JsonProperty
    private String line_origin;
    @JsonProperty
    private String line_destiny;
}
