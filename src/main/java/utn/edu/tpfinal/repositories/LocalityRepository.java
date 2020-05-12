package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.Locality;

import java.util.List;
@Repository
public interface LocalityRepository extends JpaRepository<Locality, Integer> {
    @Query(value = "select * from localities where locality_name = ?1", nativeQuery = true)
    List<Locality> findByLocalityName(String name);
}
