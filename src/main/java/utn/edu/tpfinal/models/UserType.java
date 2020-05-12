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
@Table(name = "user_types")
public class UserType {

    @Id
    @Column(name = "id_user_type")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUserType;

    @Column(name = "usertype_name")
    @NotNull
    private String userTypeName;

    @OneToMany(mappedBy = "userType")
    private List<User> users;
}
