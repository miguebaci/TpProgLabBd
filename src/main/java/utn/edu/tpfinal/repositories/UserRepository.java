package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.Users;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
  List<Users> findByDni(Integer dni);
}
