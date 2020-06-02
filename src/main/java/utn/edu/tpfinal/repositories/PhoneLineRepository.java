package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.PhoneLine;

import java.util.List;

@Repository
public interface PhoneLineRepository extends JpaRepository<PhoneLine, Integer> {
    @Query(value = "select * from phone_lines where prefix = :prefix ;", nativeQuery = true)
    List<PhoneLine> findAllPhoneLinesByPrefix(@Param("prefix")  Integer prefix);
}
