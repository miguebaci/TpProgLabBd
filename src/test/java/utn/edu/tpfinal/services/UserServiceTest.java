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
import utn.edu.tpfinal.dto.UserResponseDTO;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.models.UserType;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.repositories.UserRepository;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IReduceUser reduceUser;

    @Test
    public void getOneReduceUserTest() {
        User u = new User(1, UserType.client, 37867266, "dema", "felipe", "demaria", "dema22", false, null, null);
        Mockito.when(userRepository.findReduceById(u.getId())).thenReturn(reduceUser);
        IReduceUser response = userService.getOneReduceUser(u.getId());

        Assertions.assertEquals(reduceUser, response);
    }

    @Test
    public void getOneUserTest() {
        User u = new User(1, UserType.client, 37867266, "dema", "felipe", "demaria", "dema22", false, null, null);
        Mockito.when(userRepository.findById(u.getId())).thenReturn(Optional.ofNullable(u));
        Optional<User> response = userService.getOneUser(u.getId());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(u, response.get());
    }


    @Test
    public void getAllUsersTest() throws ResourceNotExistException {
        List<IReduceUser> list = new ArrayList<>();
        list.add(reduceUser);
        when(userRepository.findAllUsersReduce()).thenReturn(list);
        List<IReduceUser> response = userService.getAllUsers();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(list, response);
    }

    @Test
    public void addUserTest() throws ResourceAlreadyExistException, NoSuchAlgorithmException {
        User u = new User(null, UserType.client, 12345678, "dema", "felipe", "demaria", "dema22", false, null, null);
        Mockito.when(userRepository.findByDni(u.getDni())).thenReturn(Optional.ofNullable(isNotNull()));
        Mockito.when(userRepository.save(u)).thenReturn(u);
        User response = userService.addUser(u);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(u, response);

        Mockito.when(userRepository.findByDni(u.getDni())).thenReturn(Optional.ofNullable(u));
        Assertions.assertThrows(ResourceAlreadyExistException.class, () -> {
            userService.addUser(u);
        });
    }


    @Test
    public void deleteOneUserTest() throws ResourceNotExistException, NoSuchAlgorithmException, ResourceAlreadyExistException, URISyntaxException {
        when(userService.getOneUser(1)).thenReturn(Optional.of(new User()));
        doNothing().when(userRepository).deleteById(1);
        userService.deleteOneUser(1);
        verify(userRepository, times(1)).deleteById(1);

        when(userService.getOneUser(1)).thenThrow(NoSuchElementException.class);
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            userService.getOneUser(1);
        });
    }


    @Test
    public void updateOneUserTest() throws NoSuchAlgorithmException, ResourceNotExistException {
        User u = new User(1, UserType.client, 12345678, "dema", "felipe", "demaria", "dema22", false, null, null);
        User updated = new User(1, UserType.client, 12345678, "dema22", "felipe", "demaria", userService.hashPass("dema22"), false, null, null);
        int id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.of(u));
        when(userRepository.save(u)).thenReturn(u);
        userService.updateOneUser(updated, id);

        verify(userRepository,times(1)).findById(1);
        verify(userRepository,times(1)).save(u);

        when(userService.getOneUser(1)).thenThrow(NoSuchElementException.class);
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            userService.getOneUser(1);
        });
    }

    @Test
    public void loginTest() throws NoSuchAlgorithmException, ResourceNotExistException {

        User user = new User();
        String username = "migue";
        String password = "abc123";
        user.setUsername(username);
        user.setPass(password);
        when(userRepository.userExists(username, userService.hashPass(password))).thenReturn(user);
        User response = userService.login(username, password);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(user, response);
    }

    @Test
    public void exceptionLoginTest() {
        User user = new User();
        String username = "migue";
        String password = "abc123";
        user.setUsername(username);
        user.setPass(password);
        when(userRepository.userExists(username, password)).thenReturn(null);

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            userService.login(username, password);
        });
    }

    @Test
    public void getOneDTOUserTest() {
        UserResponseDTO userResponseDTO = new UserResponseDTO(12345678, "dema", "felipe", "demaria", null, null);
        UserResponseDTO responseDTO = new UserResponseDTO();
        User user = new User(1, UserType.client, 12345678, "dema", "felipe", "demaria", "dema22", false, null, null);
        when(userService.getOneUser(1)).thenReturn(Optional.of(user));
        responseDTO.setDni(user.getDni());
        responseDTO.setName(user.getName());
        responseDTO.setSurname(user.getSurname());
        responseDTO.setUsername(user.getUsername());

        Assertions.assertNotNull(user);
        Assertions.assertEquals(userResponseDTO, responseDTO);
    }

    @Test
    public void getOneDTOUserExceptionTest() {
        when(userRepository.getOne(1)).thenReturn(null);

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            userService.getOneDTOUser(1);
        });
    }

    @Test
    public void activeUserTest() throws ResourceNotExistException, NoSuchAlgorithmException {
        User u = new User(1, UserType.client, 12345678, "dema", "felipe", "demaria", "dema22", false, null, null);
        User updated = new User(1, UserType.client, 12345678, "dema", "felipe", "demaria", "dema22", true, null, null);
        int id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.of(u));
        when(userRepository.save(u)).thenReturn(updated);
        userService.activeUser(id);

        Assertions.assertEquals(u, updated);

        when(userService.getOneUser(1)).thenThrow(NoSuchElementException.class);
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            userService.getOneUser(1);
        });
    }

    @Test
    public void activeUserTest2() throws ResourceNotExistException, NoSuchAlgorithmException {
        User u = new User(1, UserType.client, 12345678, "dema", "felipe", "demaria", "dema22", true, null, null);
        User updated = new User(1, UserType.client, 12345678, "dema", "felipe", "demaria", "dema22", false, null, null);
        int id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.of(u));
        when(userRepository.save(u)).thenReturn(updated);
        userService.activeUser(id);

        Assertions.assertEquals(u, updated);

        when(userRepository.findById(id)).thenThrow(NoSuchElementException.class);
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            userService.getOneUser(id);
        });
    }
}
