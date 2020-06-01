package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.dto.BillForUserDTO;
import utn.edu.tpfinal.dto.UserRespondeDTO;
import utn.edu.tpfinal.models.Bill;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.repositories.BillRepository;
import utn.edu.tpfinal.repositories.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final BillService billService;
    private final PhoneLineService phoneLineService;

    @Autowired
    public UserService(BillRepository billRepository, UserRepository userRepository, BillService billService, PhoneLineService phoneLineService) {
        this.billRepository = billRepository;
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

        List<Bill> billsCurrentUser = billRepository.getUserBillInfo(idUser);
        List<BillForUserDTO> billForUserDTO = new ArrayList<BillForUserDTO>();

        for(Bill b: billsCurrentUser){
            billForUserDTO.add(new BillForUserDTO(b.getTotalPrice(), b.getEmittionDate(), b.getExpirationDate(), b.isBillStatus()));
        }

        // We set the info to our response dto
        userRespondeDTO.setDni(currentUser.getDni());
        userRespondeDTO.setName(currentUser.getName());
        userRespondeDTO.setSurname(currentUser.getSurname());
        userRespondeDTO.setUsername(currentUser.getUsername());
        userRespondeDTO.setBills(billForUserDTO);

        return userRespondeDTO;
    }
}
