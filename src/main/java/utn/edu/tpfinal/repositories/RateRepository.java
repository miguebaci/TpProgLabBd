package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.Rate;

@Repository
public interface RateRepository extends JpaRepository<Rate, Integer>  {
}
