package utn.edu.tpfinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="provinces")


public class Province {
    @Id
    @Column(name = "id_prov")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //The @GeneratedValue annotation is to configure the way of increment of the specified column(field)
    private Integer idProv;

    @Column(name = "province_name")
    @NotNull
    private String provinceName;

    @OneToMany(mappedBy = "province")
    private List<Locality> localities;
}
