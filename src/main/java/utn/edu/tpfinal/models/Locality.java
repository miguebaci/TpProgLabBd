package utn.edu.tpfinal.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Table(name="localities")
@Entity
public class Locality {
    @Id
    @NotNull
    private Integer prefix;

    @Column(name = "locality_name")
    private String localityName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prov")
    @JsonBackReference
    private Province province;

    public Integer getProvinceId(){ return province.getIdProv(); }
}
