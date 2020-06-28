package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.Exceptions.ResourceAlreadyExistException;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.dto.BillForUserDTO;
import utn.edu.tpfinal.dto.PhoneLineForUserDTO;
import utn.edu.tpfinal.dto.UserResponseDTO;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.repositories.UserRepository;
import utn.edu.tpfinal.session.SessionManager;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.NoSuchElementException;
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

    public List<IReduceUser> getAllUsers() {
        return userRepository.findAllUsersReduce();
    }

    public User addUser(User newUser) throws NoSuchAlgorithmException, ResourceAlreadyExistException {
        Boolean exists = userRepository.findByDni(newUser.getDni()).isPresent();

        if (!exists) {
            User created = userRepository.save(User.builder()
                    .userType(newUser.getUserType())
                    .dni(newUser.getDni())
                    .username(newUser.getUsername())
                    .name(newUser.getName())
                    .surname(newUser.getSurname())
                    .pass(hashPass(newUser.getPass()))
                    .suspended(false)
                    .build());
            return created;
        } else throw new ResourceAlreadyExistException("The user you are trying to save already exits");
    }

    public void deleteOneUser(Integer idUser) {
        try {
            Optional<User> u = getOneUser(idUser);
            User myUser = u.get();
            userRepository.deleteById(idUser);
        }catch (NoSuchElementException e){
            throw e;
        }
    }

    public void updateOneUser(User newUser, Integer idUser) throws NoSuchAlgorithmException, ResourceNotExistException {
        try {
            Optional<User> resultUser = getOneUser(idUser);
            //System.out.println(resultUser);
            User currentUser = resultUser.get();
            //System.out.println(currentUser);

            if (resultUser != null) {
                currentUser.setId(newUser.getId());
                currentUser.setUserType(newUser.getUserType());
                currentUser.setDni(newUser.getDni());
                currentUser.setUsername(newUser.getUsername());
                currentUser.setName(newUser.getName());
                currentUser.setSurname(newUser.getSurname());
                if (!currentUser.getPass().equals(hashPass(newUser.getPass()))) {
                    currentUser.setPass(hashPass(newUser.getPass()));
                }
                currentUser.setPhoneLines(newUser.getPhoneLines());
                currentUser.setBills(newUser.getBills());

                userRepository.save(currentUser);
            }
        }catch(NoSuchElementException e){
            throw new ResourceNotExistException("The user you want to update does not exist");
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

    public UserResponseDTO getOneDTOUser(Integer idUser) throws ResourceNotExistException {

        try {
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

        } catch (NoSuchElementException e) {
            throw new ResourceNotExistException("The user you are trying to serach does not exists. Verify the user id and try again");
        }
    }

    public void activeUser(Integer idUser) throws ResourceNotExistException {
        try {
            Optional<User> resultUser = getOneUser(idUser);
            User currentUser = resultUser.get();
            if (resultUser != null) {
                if (currentUser.getSuspended()) {
                    currentUser.setSuspended(false);
                } else currentUser.setSuspended(true);
                userRepository.save(currentUser);
            }
        }catch(NoSuchElementException e){
            throw new ResourceNotExistException("The user you want to activate does not exist");
        }
    }

}
