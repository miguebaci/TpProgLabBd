package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.PhoneLine;

@Repository
public interface PhoneLineRepository extends JpaRepository<PhoneLine, Integer> {
}
