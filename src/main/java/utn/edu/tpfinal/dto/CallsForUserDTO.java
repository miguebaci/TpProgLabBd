package utn.edu.tpfinal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CallsForUserDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float price;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.000")
    private Date dateCall;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.000")
    private Date  hourCallFinish;

    @JsonProperty
    private int duration;

    @JsonProperty
    private String numberOrigin;

    @JsonProperty
    private String numberDestiny;
}