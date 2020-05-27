package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  List<User> findByDni(Integer dni);
  @Query(value = "select * from users where id = ?1", nativeQuery = true)
  List<User> getById(Integer id);

  @Query(value = "select * from users where username = ?1 and pass = ?2", nativeQuery = true)
  User userExists(String username, String password);
}
