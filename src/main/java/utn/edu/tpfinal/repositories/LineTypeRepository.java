package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.LineType;

@Repository
public interface LineTypeRepository extends JpaRepository<LineType, Integer> {

}
