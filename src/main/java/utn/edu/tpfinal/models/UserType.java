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
@Table(name = "user_types")
public class UserType {

    @Id
    @Column(name = "id_user_type")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUserType;

    @Column(name = "usertype_name")
    @NotNull
    private String userTypeName;

    @Transient
    private List<User> users;
}
