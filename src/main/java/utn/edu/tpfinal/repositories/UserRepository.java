package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.dto.BillForUserDTO;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.projections.IReduceUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from users where username = ?1 and pass = ?2", nativeQuery = true)
    User userExists(String username, String password);

    @Query(value = "select u.dni, u.username, u.name, u.surname\n" +
                    "from users u\n" +
                    "where id = :idUser ;\n", nativeQuery = true)
    IReduceUser findReduceById(@Param("idUser") Integer idUser);

    @Query(value = "select dni, username, name, surname from users ;\n", nativeQuery = true)
    List<IReduceUser> findAllUsersReduce();

    Optional<User> findByDni(Integer dni);
}
