package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.Province;

import java.util.List;
@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
    @Query(value = "select * from provinces where province_name = ?1", nativeQuery = true)
    List<Province> findByProvinceName(String name);

    @Query(value = "select * from provinces where id_prov = ?1", nativeQuery = true)
    Province findByProvinceId(Integer id);
}
