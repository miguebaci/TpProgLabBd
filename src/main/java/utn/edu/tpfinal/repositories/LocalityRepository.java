package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.Locality;
@Repository
public interface LocalityRepository extends JpaRepository<Locality, Integer> {
}
