package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.dto.BillForUserDTO;
import utn.edu.tpfinal.dto.PhoneLineForUserDTO;
import utn.edu.tpfinal.dto.UserResponseDTO;
import utn.edu.tpfinal.models.Bill;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.repositories.BillRepository;
import utn.edu.tpfinal.repositories.UserRepository;
import utn.edu.tpfinal.session.SessionManager;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BillService billService;
    private final PhoneLineService phoneLineService;
    private final SessionManager sessionManager;

    @Autowired
    public UserService(UserRepository userRepository,
                       BillService billService,
                       PhoneLineService phoneLineService,
                       SessionManager sessionManager) {
        this.userRepository = userRepository;
        this.billService = billService;
        this.phoneLineService = phoneLineService;
        this.sessionManager = sessionManager;
    }

    public Optional<User> getOneUser(Integer idUser) {
        return userRepository.findById(idUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public ResponseEntity<User> addUser(User newUser) throws NoSuchAlgorithmException, UserNotExistException {
        User exists = userRepository.findById(newUser.getDni()).get();
        if (exists != null) {
            User created = userRepository.save(User.builder()
                    .dni(newUser.getDni())
                    .username(newUser.getUsername())
                    .name(newUser.getName())
                    .surname(newUser.getSurname())
                    .pass(hashPass(newUser.getPass()))
                    .build());
            return ResponseEntity.ok(created);
        } else throw new UserNotExistException();
    }

    public void deleteOneUser(Integer idUser) {
        userRepository.deleteById(idUser);
    }

    public void updateOneUser(User newUser, Integer idUser) throws NoSuchAlgorithmException, UserNotExistException {
        Optional<User> resultUser = getOneUser(idUser);
        User currentUser = resultUser.get();

        if (resultUser != null) {
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

    public User login(String username, String password) throws NoSuchAlgorithmException, ResourceNotExistException {
        User user = userRepository.userExists(username, hashPass(password));
        return Optional.ofNullable(user).orElseThrow(() -> new ResourceNotExistException("There is no user with the information you have provided."));
    }

    private String hashPass(String pass) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        byte[] data = pass.getBytes();
        m.update(data, 0, data.length);
        BigInteger i = new BigInteger(1, m.digest());
        return String.format("%1$032X", i);
    }

    public IReduceUser getOneReduceUser(Integer idUser) {
        return userRepository.findReduceById(idUser);
    }

    public UserResponseDTO getOneDTOUser(Integer idUser) {

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        Optional<User> resultUser = getOneUser(idUser);
        User currentUser = resultUser.get();

        List<BillForUserDTO> billForUserDTO = billService.getBillsForUserDTO(idUser);
        List<PhoneLineForUserDTO> phoneLineForUserDTO = phoneLineService.getPhoneLinesForUserDTO(idUser);

        // We set the info to our response dto
        userResponseDTO.setDni(currentUser.getDni());
        userResponseDTO.setName(currentUser.getName());
        userResponseDTO.setSurname(currentUser.getSurname());
        userResponseDTO.setUsername(currentUser.getUsername());
        userResponseDTO.setBills(billForUserDTO);
        userResponseDTO.setPhoneLines(phoneLineForUserDTO);
        return userResponseDTO;
    }

    public void activeUser(Integer idUser) throws NoSuchAlgorithmException, UserNotExistException {
        Optional<User> resultUser = getOneUser(idUser);
        User currentUser = resultUser.get();
        if (resultUser != null) {
            if (currentUser.getSuspended()) {
                currentUser.setSuspended(false);
            } else currentUser.setSuspended(true);
            // When we call add user method we will use the save JPA method which will update BECAUSE
            // the method is based on id value, if an id exists it merge (updated) the entity otherwise
            // it will save a new entity.
            addUser(currentUser);
        }

    }
}
