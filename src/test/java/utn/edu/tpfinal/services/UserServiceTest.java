package utn.edu.tpfinal.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import utn.edu.tpfinal.Exceptions.ResourceAlreadyExistException;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.models.*;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.projections.ITop10DestinationCalled;
import utn.edu.tpfinal.repositories.UserRepository;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BillService billService;

    @Mock
    private PhoneLineService phoneLineService;

    @Mock
    private IReduceUser iReduceUser;

    @Test
    public void getOneReduceUserTest(){
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        Mockito.when(userRepository.findReduceById(u.getId())).thenReturn(iReduceUser);
        IReduceUser response =  userService.getOneReduceUser(u.getId());

        Assertions.assertEquals(iReduceUser,response);
    }

    @Test
    public void getOneUserTest() {
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        Mockito.when(userRepository.findById(u.getId())).thenReturn(Optional.ofNullable(u));
        Optional<User> response =  userService.getOneUser(u.getId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(u,response.get());
    }

    /*@Test
    public void getAllUsersTest() {
        List<IReduceUser> listReduceUser = new ArrayList<>();
        IReduceUser reduceUser = new ReduceUserImpl();
        IReduceUser reduceUserTwo = new ReduceUserImpl();

        reduceUser.setDni(37867266);
        reduceUser.setName("feli");
        reduceUser.setSurname("demaria");
        reduceUser.setUsername("dema");

        reduceUserTwo.setDni(37867261);
        reduceUserTwo.setName("migue");
        reduceUserTwo.setSurname("demaria");
        reduceUserTwo.setUsername("migue22");

        listReduceUser.add(reduceUser);
        listReduceUser.add(reduceUserTwo);

        Mockito.when(userRepository.findAllUsersReduce()).thenReturn(listReduceUser);
        List<IReduceUser> response = userService.getAllUsers();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(listReduceUser, response);
        System.out.println(listReduceUser.get(0).getDni());
    }
    */

    /*@Test
    public void addUserTest() throws ResourceAlreadyExistException, NoSuchAlgorithmException {
        User u = new User(null, UserType.client,12345678,"dema","felipe","demaria","dema22",false,null,null);
        Mockito.when(userRepository.findByDni(u.getDni())).thenReturn(Optional.ofNullable(u));
        Mockito.when(userRepository.save(u)).thenReturn(u);
        User response = userService.addUser(u);
        Mockito.verify(userRepository,Mockito.times(1)).save(u);

        //Assertions.assertNotNull(response);
        //Assertions.assertEquals(u,response);
    }
    */

    /*
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

   */




}
