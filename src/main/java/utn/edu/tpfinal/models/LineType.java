package utn.edu.tpfinal.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name="line_types")
@Entity
public class LineType {

    @Id
    @Column(name = "id_line_type")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLineType;

    @Column(name = "line_type_name")
    private String lineTypeName;

    @Transient
    private List<PhoneLine> phoneLines;
}
