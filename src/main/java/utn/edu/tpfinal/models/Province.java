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
@Table(name = "provinces")
@Entity
public class Province {
    @Id
    @Column(name = "id_prov")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //The @GeneratedValue annotation is to configure the way of increment of the specified column(field)
    private Integer idProv;

    @Column(name = "province_name")
    private String provinceName;

    //If you annotate a field with @Transient it will not be persisted.
    @Transient
    private List<Locality> localities;

    /*@JsonManagedReference
    @OneToMany(mappedBy = "province",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Locality> localities;

    @Override public String toString() {
        return "";
    }
    */
}
