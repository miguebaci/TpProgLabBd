package utn.edu.tpfinal.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="localities")

public class Locality {
    @Id
    @NotNull
    private Integer prefix;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "id_prov")
    private Province province;

    @NotNull
    @Column(name = "locality_name")
    private String localityName;
}
