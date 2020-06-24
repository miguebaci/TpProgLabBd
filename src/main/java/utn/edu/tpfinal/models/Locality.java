package utn.edu.tpfinal.models;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Table(name = "localities")
@Entity
public class Locality {
    @Id
    @NotNull
    private Integer prefix;

    @Column(name = "locality_name")
    private String localityName;

    // @ManyToOne indicates that Many Locality tuples can refer to one Province tuple
    // optional=false means this relationship becomes mandatory , no locality row can be saved without a province tuple reference
    //@JoinColumn says that there is a column ID_PROV in Locality table which will refer(foreign key) to primary key of the Province table.
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_prov")
    private Province province;

    @Transient
    private List<PhoneLine> phoneLines;

    @Transient
    private List<Rate> rates;

    //----------------------------------------------------------------

    /*
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_prov")
    private Province province;

    @Transient
    private Province from;

    @JsonBackReference
    public Integer getProvinceId(){
        return province.getIdProv();
    }

    @Override public String toString() {
        return "";
    }*/
}
