package utn.edu.tpfinal.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Table(name="calls")
@Entity

public class Call {
    @Id
    @Column(name = "id_call")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCall;

    @ManyToOne//(optional = false)
    @JoinColumn(name = "line_origin")
    private PhoneLine lineOrigin;

    @ManyToOne//(optional = false)
    @JoinColumn(name = "line_destiny")
    private PhoneLine lineDestiny;

    @ManyToOne//(optional = false)
    @JoinColumn(name = "id_bill")
    private Bill bill;

    @ManyToOne//(optional = false)
    @JoinColumn(name = "id_rate")
    private Rate rate;

    private Float price;
    private Float cost;
    private Float profit;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.000")
    @Column(name= "date_call")
    private Date  dateCall;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss.000")
    @Column(name= "hour_call_finish")
    private Date  hourCallFinish;

    private int duration;

    @Column(name= "number_origin")
    private String numberOrigin;

    @Column(name= "number_destiny")
    private String numberDestiny;
}
