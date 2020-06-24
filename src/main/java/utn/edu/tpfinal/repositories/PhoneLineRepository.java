package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.models.User;

import java.util.Optional;

import java.util.List;

@Repository
public interface PhoneLineRepository extends JpaRepository<PhoneLine, Integer> {
    @Query(value = "select * from phone_lines where id_user = :idUser ;", nativeQuery = true)
    List<PhoneLine> getUserPhoneLines(Integer idUser);

    @Query(value = "select * from phone_lines where line_number = :lineNumber and id_user = :idUser ;", nativeQuery = true)
    PhoneLine getPhoneLineByUserId(@Param("lineNumber") String lineNumber,
                                   @Param("idUser") Integer idUser);
}
