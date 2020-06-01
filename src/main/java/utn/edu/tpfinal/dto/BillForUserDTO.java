package utn.edu.tpfinal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class BillForUserDTO {
    @JsonProperty
    private Float totalPrice;
    @JsonProperty
    private Date emittionDate;
    @JsonProperty
    private Date expirationDate;
    @JsonProperty
    private boolean billStatus;
}
