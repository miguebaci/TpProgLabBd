package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.Call;

@Repository
public interface CallRepository extends JpaRepository<Call, Integer> {
}
