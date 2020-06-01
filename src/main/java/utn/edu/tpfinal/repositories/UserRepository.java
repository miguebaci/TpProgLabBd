package utn.edu.tpfinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utn.edu.tpfinal.models.Bill;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.projections.IReduceUser;

import java.sql.ResultSet;
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


  @Query(value = "select * from bills where id_user = ?1", nativeQuery = true)
  ResultSet getUserBillInfo(Integer idUser);

  @Query(value = "select * from phone_lines where id_user = ?1", nativeQuery = true)
  ResultSet getUserPhoneLineInfo(Integer idUser);



  /*@Query(value = "select u.dni, u.username, u.name, u.surname\n" +
          "from users u\n" +
          "where id = ?1;\n", nativeQuery = true)
  User findUserInfoById(Integer idUser);*/
}
