package utn.edu.tpfinal.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name="provinces")
@Entity
public class Province {
    @Id
    @Column(name = "id_prov")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //The @GeneratedValue annotation is to configure the way of increment of the specified column(field)
    private Integer idProv;

    @Column(name = "province_name")
    private String provinceName;

    @OneToMany(mappedBy = "province")
    private List<Locality> localities;
}
