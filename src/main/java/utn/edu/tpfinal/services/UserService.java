package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.dto.UserRespondeDTO;
import utn.edu.tpfinal.models.Bill;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.repositories.BillRepository;
import utn.edu.tpfinal.repositories.PhoneLineRepository;
import utn.edu.tpfinal.repositories.UserRepository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BillService billService;
    private final PhoneLineService phoneLineService;

    @Autowired
    public UserService(UserRepository userRepository, BillService billService, PhoneLineService phoneLineService) {
        this.userRepository = userRepository;
        this.billService = billService;
        this.phoneLineService = phoneLineService;
    }

    public Optional<User> getOneUser(Integer idUser) {
        return userRepository.findById(idUser);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(User newUser) {
        userRepository.save(newUser);
    }

    public void deleteOneUser(Integer idUser) {
        userRepository.deleteById(idUser);
    }

    public void updateOneUser(User newUser, Integer idUser) {
        Optional<User> resultUser = getOneUser(idUser);
        User currentUser = resultUser.get();

        if(resultUser != null) {
            currentUser.setId(newUser.getId());
            currentUser.setUserType(newUser.getUserType());
            currentUser.setDni(newUser.getDni());
            currentUser.setUsername(newUser.getUsername());
            currentUser.setName(newUser.getName());
            currentUser.setSurname(newUser.getSurname());
            currentUser.setPass(newUser.getPass());
            currentUser.setPhoneLines(newUser.getPhoneLines());
            currentUser.setBills(newUser.getBills());

            // When we call add user method we will use the save JPA method which will update BECAUSE
            // the method is based on id value, if an id exists it merge (updated) the entity otherwise
            // it will save a new entity.

            addUser(currentUser);
        }

    }

    public User login(String username, String password) {
        User user = userRepository.userExists(username, password);
        return Optional.ofNullable(user).orElseThrow(() -> new RuntimeException("User does not exists"));
    }

    public IReduceUser getOneReduceUser(Integer idUser) {
        return userRepository.findReduceById(idUser);
    }


    public UserRespondeDTO getOneDTOUser(Integer idUser) throws SQLException {

        UserRespondeDTO userRespondeDTO = new UserRespondeDTO();

        Optional<User> resultUser = getOneUser(idUser);
        User currentUser = resultUser.get();

        ResultSet rs = userRepository.getUserBillInfo(idUser);
        List<Bill> currentBill = new ArrayList<>();


        try{
            while (rs.next()) {
                float totalPrice = rs.getFloat("total_price");
                Date emittionDate = rs.getDate("emittion_date");
                Date expirationDate = rs.getDate("expiration_date");
                Boolean billStatus = rs.getBoolean("bill_status");

                Bill bill = new Bill();
                bill.setTotalPrice(totalPrice);
                bill.setEmittionDate(emittionDate);
                bill.setExpirationDate(expirationDate);
                bill.setBillStatus(billStatus);

                currentBill.add(bill);
            }

        } catch(SQLException e) {
           throw new SQLException();
        }

        //List<Bill> billsCurrentUser = userRepository.getUserBillInfo(idUser);
        //List<PhoneLine> phoneLinesCurrentUser = userRepository.getUserPhoneLineInfo(idUser);

        // We set the info to our response dto
        userRespondeDTO.setDni(currentUser.getDni());
        userRespondeDTO.setName(currentUser.getName());
        userRespondeDTO.setSurname(currentUser.getSurname());
        userRespondeDTO.setUsername(currentUser.getUsername());
        //userRespondeDTO.setPhoneLines(phoneLinesCurrentUser);
        userRespondeDTO.setBills(currentBill);

        return userRespondeDTO;
    }
}
