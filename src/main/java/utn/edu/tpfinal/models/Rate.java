package utn.edu.tpfinal.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name="rates")
@Entity

public class Rate {
    @Id
    @Column(name = "id_rate")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prefix_origin")
    private Locality localityOrigin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prefix_destiny")
    private Locality localityDestiny;

    @Column(name = "price_per_minute")
    private Float pricePerMinute;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "expiration_date")
    private Date expirationDate;

    private Float cost;

    @Transient
    List<Call> calls;
}
