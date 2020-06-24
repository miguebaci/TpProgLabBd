package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.dto.BillForUserDTO;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.projections.IReduceUser;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from users where username = ?1 and pass = ?2", nativeQuery = true)
    User userExists(String username, String password);

    @Query(value = "select u.dni, u.username, u.name, u.surname, p.line_number\n" +
            "from users u\n" +
            "inner join phone_lines p\n" +
            "on u.id = p.id_line\n" +
            "where id = ?1;\n", nativeQuery = true)
    IReduceUser findReduceById(Integer idUser);


  /*@Query(value = "select total_price as totalPrice," +
                  " emittion_date as emittionDate," +
                  " expiration_date as expiratioDate," +
                  " bill_status as billStatus" +
                  " from bills" +
                  " where id_user = :idUser ;", nativeQuery = true)
  List<BillForUserDTO> getUserBillInfo(@Param("idUser") Integer idUser);*/

    @Query(value = "SELECT new utn.edu.tpfinal.dto.BillForUserDTO(b.totalPrice, b.emittionDate, b.expirationDate, b.billStatus) from bills where id_user = :idUser ;", nativeQuery = true)
    List<BillForUserDTO> getUserBillInfo(@Param("idUser") Integer idUser);

}
