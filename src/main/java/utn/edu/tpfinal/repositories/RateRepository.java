package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.Rate;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer> {

    @Query(value = "select * from rates where rates.prefix_origin = :idLocalityOrigin and rates.prefix_destiny = :idLocalityDestiny", nativeQuery = true)
    Optional<Rate> getRatesByLocality(@Param("idLocalityOrigin")Integer idLocalityOrigin, @Param("idLocalityDestiny") Integer idLocalityDestiny);
}
