package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.Province;
@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
}
