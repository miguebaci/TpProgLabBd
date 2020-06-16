package utn.edu.tpfinal.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotNull
    private enum UserType {backoffice, client};
    @Enumerated(EnumType.STRING)
    private  UserType userType;

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

    @Transient
    private List<PhoneLine> phoneLines;

    @Transient
    private List<Bill> bills;

    public String getUserTypeString() {
        return userType.toString();
    }
}
