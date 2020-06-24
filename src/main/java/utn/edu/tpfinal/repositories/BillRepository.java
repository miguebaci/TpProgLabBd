package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.Bill;

import java.sql.Date;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    @Query(value = "select * from bills where id_user = :idUser ;", nativeQuery = true)
    List<Bill> getUserBillInfo(@Param("idUser") Integer idUser);

    @Query(value = "select * from bills b where b.id_user = :idUser and b.expiration_date between :fromDate and :toDate ;", nativeQuery = true)
    List<Bill> getBillsFromUserBetweenDates(@Param("idUser") Integer idUser,
                                            @Param("fromDate") Date fromDate,
                                            @Param("toDate") Date toDate);
}
