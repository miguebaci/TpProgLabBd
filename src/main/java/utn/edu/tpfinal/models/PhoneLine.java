package utn.edu.tpfinal.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Table(name="phone_lines")
@Entity
public class PhoneLine {
    @Id
    @Column(name = "id_line")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLine;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prefix")
    private Locality locality;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_line_type")
    private LineType lineType;

    @Column(name = "line_number")
    private String lineNumber;

    @Transient
    List<Call> calls;
}
