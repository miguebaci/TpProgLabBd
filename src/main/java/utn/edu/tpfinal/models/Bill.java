package utn.edu.tpfinal.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "bills")
@Entity

public class Bill {
    @Id
    @Column(name = "id_bill")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBill;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "total_price")
    private Float totalPrice;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "emittion_date")
    private Date emittionDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "bill_status")
    private boolean billStatus;

    @Column(name = "total_cost")
    private Float totalCost;

    @Column(name = "total_profit")
    private Float totalProfit;

    @Transient
    List<Call> calls;
}
