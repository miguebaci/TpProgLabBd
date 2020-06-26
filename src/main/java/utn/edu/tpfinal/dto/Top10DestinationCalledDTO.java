package utn.edu.tpfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utn.edu.tpfinal.projections.ITop10DestinationCalled;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Top10DestinationCalledDTO {
    private List<ITop10DestinationCalled> list;

    public static Top10DestinationCalledDTO toList(List<ITop10DestinationCalled> topTen){
        return Top10DestinationCalledDTO.builder()
                .list(topTen)
                .build();
    }
}
