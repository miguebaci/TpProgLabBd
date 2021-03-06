package utn.edu.tpfinal.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    public UserType userType;

    @NotNull
    private Integer dni;

    @NotNull
    private String username;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String pass;

    private Boolean suspended;

    @Transient
    private List<PhoneLine> phoneLines;

    @Transient
    private List<Bill> bills;

    @JsonIgnore
    public String getUserTypeString() {
        return userType.toString();
    }
}
