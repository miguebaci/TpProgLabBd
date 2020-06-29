package utn.edu.tpfinal.controllers;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import utn.edu.tpfinal.Exceptions.ResourceAlreadyExistException;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.Exceptions.ValidationException;
import utn.edu.tpfinal.dto.UserResponseDTO;
import utn.edu.tpfinal.models.Bill;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.models.UserType;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.services.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private IReduceUser iReduceUser;

    @Test
    public void getReduceUserTest() {
        User u = new User();
        u.setId(1);
        u.setUserType(UserType.client);
        u.setPhoneLines(new ArrayList<PhoneLine>());
        u.setBills(new ArrayList<Bill>());
        u.setUsername("dema");
        u.setDni(37867266);
        u.setName("felipe");
        u.setPass("dema22");

        Mockito.when(userService.getOneReduceUser(u.getId())).thenReturn(iReduceUser);
        IReduceUser response =  userController.getReduceUser(u.getId());

        Assertions.assertEquals(iReduceUser,response);
        Assertions.assertNotNull(response);
    }

    @Test
    public void getOneUserDTOTest () throws ResourceNotExistException {
        User u = new User();
        u.setId(1);
        u.setUserType(UserType.client);
        u.setPhoneLines(new ArrayList<PhoneLine>());
        u.setBills(new ArrayList<Bill>());
        u.setUsername("dema");
        u.setDni(37867266);
        u.setName("felipe");
        u.setPass("dema22");
        u.setSurname("demaria");

        UserResponseDTO userResponseDTO = new UserResponseDTO(37867266,"dema", "felipe","demaria", null,null);

        Mockito.when(userService.getOneDTOUser(u.getId())).thenReturn(userResponseDTO);
        UserResponseDTO response =  userController.getOneUserDTO(u.getId());

        Assertions.assertEquals(userResponseDTO,response);
        Assertions.assertNotNull(response);
    }


    @Test
    public void activeUserTest() throws ResourceNotExistException {
        User userToUpdate = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        Mockito.doNothing().when(userService).activeUser(userToUpdate.getId());
        userController.activeUser(userToUpdate.getId());
        Mockito.verify(userService, Mockito.times(1)).activeUser(userToUpdate.getId());
    }

    @Test
    public void updateUserTest() throws ResourceNotExistException, NoSuchAlgorithmException {
        User userToUpdate = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        Mockito.doNothing().when(userService).updateOneUser(userToUpdate, userToUpdate.getId());
        userController.updateUser(userToUpdate, userToUpdate.getId());
        Mockito.verify(userService, Mockito.times(1)).updateOneUser(userToUpdate, userToUpdate.getId());
    }

    @Test
    public void deleteUserTest() throws ResourceNotExistException {
        User userToUpdate = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        Mockito.doNothing().when(userService).deleteOneUser(userToUpdate.getId());
        userController.deleteUser(userToUpdate.getId());
        Mockito.verify(userService, Mockito.times(1)).deleteOneUser(userToUpdate.getId());

        // Test exception
        doThrow(NoSuchElementException.class)
                .when(userService)
                .deleteOneUser(null);

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            userController.deleteUser(null);
        });
    }

    @Test
    public void loginTest() throws ResourceNotExistException, NoSuchAlgorithmException, ValidationException {
        // Test  OK ! login
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        Mockito.when(userService.login(u.getUsername(), u.getPass())).thenReturn(u);
        User response =  userController.login(u.getUsername(), u.getPass());

        Assertions.assertEquals(u,response);
        Assertions.assertNotNull(response);

        // Test a invalid login
        Assertions.assertThrows(ValidationException.class, () -> {
            userController.login(null,null);
        });
    }

    @Test
    public void getUsersTest () {
        List<IReduceUser> list = new ArrayList<>();
        Mockito.when(userService.getAllUsers()).thenReturn(list);
        List<IReduceUser> response =  userController.getUsers();

        Assertions.assertEquals(list,response);
        Assertions.assertNotNull(response);
    }

    @Test
    public void addUserTest() throws ResourceAlreadyExistException, NoSuchAlgorithmException {
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        Mockito.when(userService.addUser(u)).thenReturn(u);
        User response = userController.addUser(u);

        Assertions.assertEquals(u,response);
        Assertions.assertNotNull(response);
    }
}
