package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
}
