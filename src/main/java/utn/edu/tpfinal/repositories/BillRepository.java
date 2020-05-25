package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.Bill;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    @Query(value = "select * from bills where id_bill = ?1", nativeQuery = true)
    List<Bill> findOneByBillId(Integer idBill);
}
