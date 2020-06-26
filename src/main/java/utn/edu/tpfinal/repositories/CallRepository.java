package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.Call;
import utn.edu.tpfinal.projections.ITop10DestinationCalled;

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

    @Query(value = "select count(l.prefix) as quantity, l.locality_name as locality\n" +
            "from rates as r\n" +
            "inner join calls as c\n" +
            "on c.id_rate = r.id_rate\n" +
            "inner join phone_lines as p\n" +
            "on c.line_origin = p.id_line\n" +
            "inner join localities as l\n" +
            "on l.prefix = r.prefix_destiny\n" +
            "inner join users as u\n" +
            "on u.id  = p.id_user\n" +
            "where u.id = :idUser\n" +
            "group by locality\n" +
            "order by count(l.prefix) desc\n" +
            "limit 10 ;", nativeQuery = true)
    List<ITop10DestinationCalled> getTop10Info(@Param("idUser") Integer idUser);
}
