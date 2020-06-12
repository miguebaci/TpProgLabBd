package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.Call;

import java.sql.Date;
import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call, Integer> {
    @Query(value = "select * from calls where number_origin = :lineNumber AND DATE(date_call) >= :fromDate AND DATE(date_call) <= :toDate ;", nativeQuery = true)
    List<Call> getCallsFromUserAsCallerBetweenDates(@Param("fromDate") Date fromDate,
                                                    @Param("toDate") Date toDate,
                                                    @Param("lineNumber") String lineNumber);

    @Query(value = "select * from calls where number_destiny = :lineNumber AND DATE(date_call) >= :fromDate AND DATE(date_call) <= :toDate ; ;", nativeQuery = true)
    List<Call> getCallsFromUserAsReceiverBetweenDates(@Param("fromDate") Date fromDate,
                                                      @Param("toDate") Date toDate,
                                                      @Param("lineNumber") String lineNumber);

    @Query(value = "select * from calls where number_origin = :lineNumber ;", nativeQuery = true)
    List<Call> getCallsFromUserAsCaller(@Param("lineNumber") String lineNumber);


    @Query(value = "select * from calls where number_destiny = :lineNumber ;", nativeQuery = true)
    List<Call> getCallsFromUserAsReceiver(@Param("lineNumber") String lineNumber);
}
