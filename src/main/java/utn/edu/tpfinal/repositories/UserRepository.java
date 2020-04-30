package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
 List<User> findByDni(Integer dni);
}
