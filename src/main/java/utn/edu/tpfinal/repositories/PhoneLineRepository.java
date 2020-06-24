package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.models.User;

import java.util.Optional;

@Repository
public interface PhoneLineRepository extends JpaRepository<PhoneLine, Integer> {
    PhoneLine findByLineNumber(String lineNumber);
}
