package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import utn.edu.tpfinal.models.UserType;

import java.util.List;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
    @Query(value = "select * from user_types where usertype_name = ?1", nativeQuery = true) // Using this Native query for findByid
    List<UserType> findByName(String name);
}

