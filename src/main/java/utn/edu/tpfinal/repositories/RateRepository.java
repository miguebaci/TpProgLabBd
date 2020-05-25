package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.Rate;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer>  {
    @Query(value = "select * from rates where id_rate = ?1", nativeQuery = true)
    List<Rate> findOneByRateId(Integer idRate);
}
