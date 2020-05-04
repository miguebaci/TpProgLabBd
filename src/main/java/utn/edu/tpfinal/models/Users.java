package utn.edu.tpfinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotNull;
import javax.persistence.Id;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Users {

    @Id
    private Integer dni;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String pass;
}
