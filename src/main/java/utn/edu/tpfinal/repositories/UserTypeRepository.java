package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.edu.tpfinal.models.UserType;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
}

