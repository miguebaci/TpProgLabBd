package utn.edu.tpfinal.controllers.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;
import utn.edu.tpfinal.Exceptions.NoDataAvailable;
import utn.edu.tpfinal.Exceptions.ResourceAlreadyExistException;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.controllers.CallsController;
import utn.edu.tpfinal.controllers.PhoneLineController;
import utn.edu.tpfinal.controllers.RatesController;
import utn.edu.tpfinal.controllers.UserController;
import utn.edu.tpfinal.dto.CallsForUserDTO;
import utn.edu.tpfinal.dto.UserResponseDTO;
import utn.edu.tpfinal.models.Rate;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.models.UserType;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.session.SessionManager;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class BackofficeControllerTest {

    @InjectMocks
    private BackofficeController backofficeController;
    @Mock
    private UserController userController;
    @Mock
    private PhoneLineController phoneLineController;
    @Mock
    private RatesController ratesController;
    @Mock
    private CallsController callsController;
    @Mock
    private SessionManager sessionManager;
    @Mock
    private IReduceUser reduceUser;

    @Test
    public void getUsersTest() throws ResourceNotExistException {
        User u = new User(1, UserType.backoffice, 37867266, "dema", "felipe", "demaria", "dema22", false, null, null);
        String token = "asfsgegegeg";
        List<IReduceUser> list = new ArrayList<>();
        list.add(reduceUser);
        when(sessionManager.getCurrentUser(token)).thenReturn(u);
        when(userController.getUsers()).thenReturn(list);

        ResponseEntity<List<IReduceUser>> responseEntity = backofficeController.getUsers(token);

        Assertions.assertEquals(list, responseEntity.getBody());
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());

        when(userController.getUsers()).thenReturn(new ArrayList<IReduceUser>());

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            backofficeController.getUsers(token);
        });

        doThrow(ResourceNotExistException.class)
                .when(sessionManager)
                .getCurrentUser("abcde");

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            backofficeController.getUsers("abcde");
        });

    }

    @Test
    public void deleteUserTest() throws ResourceNotExistException, NoSuchAlgorithmException, ResourceAlreadyExistException, URISyntaxException {
        User u = new User(1, UserType.backoffice, 37867266, "dema", "felipe", "demaria", "dema22", false, null, null);
        String token = "asfsgegegeg";

        when(sessionManager.getCurrentUser(token)).thenReturn(u);
        doNothing().when(userController).deleteUser(u.getId());

        ResponseEntity response = backofficeController.deleteUser(token, u.getId());

        Assertions.assertEquals(200, response.getStatusCodeValue());

        doThrow(ResourceNotExistException.class)
                .when(sessionManager)
                .getCurrentUser("abcde");

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            backofficeController.deleteUser("abcde", u.getId());
        });
    }

    /*@Test
    public void suspendUser() throws ResourceNotExistException {
        User u = new User(1, UserType.backoffice, 37867266, "dema", "felipe", "demaria", "dema22", false, null, null);
        String token = "asfsgegegeg";

        when(sessionManager.getCurrentUser(token)).thenReturn(u);
        doNothing().when(userController).activeUser(u.getId());

        ResponseEntity responseEntity = backofficeController.suspendUser(token, u.getId());

        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());

        doThrow(ResourceNotExistException.class)
                .when(sessionManager)
                .getCurrentUser("abcde");

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            backofficeController.suspendUser("abcde", u.getId());
        });
    }

    @Test
    public void suspendphonelineTest() throws ResourceNotExistException {
        User u = new User(1, UserType.backoffice, 37867266, "dema", "felipe", "demaria", "dema22", false, null, null);
        String token = "asfsgegegeg";

        when(sessionManager.getCurrentUser(token)).thenReturn(u);
        doNothing().when(phoneLineController).activePhoneLine(1);

        ResponseEntity response = backofficeController.suspendphoneline(token, 1);

        Assertions.assertEquals(200, response.getStatusCodeValue());

        doThrow(ResourceNotExistException.class)
                .when(sessionManager)
                .getCurrentUser("abcde");

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            backofficeController.suspendphoneline("abcde", 1);
        });
    }*/

    @Test
    public void updateUserTest() throws ResourceNotExistException, NoSuchAlgorithmException {
        User u = new User(1, UserType.backoffice, 37867266, "dema", "felipe", "demaria", "dema22", false, null, null);
        String token = "asfsgegegeg";

        when(sessionManager.getCurrentUser(token)).thenReturn(u);
        doNothing().when(userController).updateUser(u, 1);

        ResponseEntity response = backofficeController.updateUser(token, u, 1);

        Assertions.assertEquals(200, response.getStatusCodeValue());

        doThrow(ResourceNotExistException.class)
                .when(sessionManager)
                .getCurrentUser("abcde");

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            backofficeController.updateUser("abcde", u, 1);
        });
    }

    @Test
    public void getRatesByLocalitiesTest() throws ResourceNotExistException {
        User u = new User(1, UserType.backoffice, 37867266, "dema", "felipe", "demaria", "dema22", false, null, null);
        String token = "asfsgegegeg";
        Rate rate = new Rate();

        when(sessionManager.getCurrentUser(token)).thenReturn(u);
        when(ratesController.getRatesByLocality(1, 2)).thenReturn(rate);
        ResponseEntity response = backofficeController.getRatesByLocalities(token, 1, 2);

        Assertions.assertEquals(200, response.getStatusCodeValue());

        doThrow(ResourceNotExistException.class)
                .when(sessionManager)
                .getCurrentUser("abcde");

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            backofficeController.getRatesByLocalities("abcde", 1, 2);
        });
    }

    @Test
    public void getCallsOfUserTest() throws ResourceNotExistException, NoDataAvailable {
        User u = new User(1, UserType.backoffice, 37867266, "dema", "felipe", "demaria", "dema22", false, null, null);
        String token = "asfsgegegeg";
        CallsForUserDTO callsForUserDTO = new CallsForUserDTO(new Date(), 1, "223123456", "2231234321");
        List<CallsForUserDTO> list = new ArrayList<>();
        list.add(callsForUserDTO);

        when(sessionManager.getCurrentUser(token)).thenReturn(u);
        when(callsController.getCallsByUser(1, "223123456")).thenReturn(list);

        ResponseEntity<List<CallsForUserDTO>> response = backofficeController.getCallsOfUser(token, 1, "223123456");

        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertEquals(list, response.getBody());

        when(callsController.getCallsByUser(1, "223123456")).thenReturn(new ArrayList<>());

        Assertions.assertThrows(NoDataAvailable.class, () -> {
            backofficeController.getCallsOfUser("abcde", 1, "223123456");
        });

        doThrow(ResourceNotExistException.class)
                .when(sessionManager)
                .getCurrentUser("abcde");

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            backofficeController.getCallsOfUser("abcde", 1, "223123456");
        });
    }

    @Test
    public void getReduceUserTest() throws ResourceNotExistException {
        User u = new User(1, UserType.backoffice, 37867266, "dema", "felipe", "demaria", "dema22", false, null, null);
        String token = "asfsgegegeg";

        when(sessionManager.getCurrentUser(token)).thenReturn(u);
        when(userController.getReduceUser(1)).thenReturn(reduceUser);

        ResponseEntity<IReduceUser> response = backofficeController.getReduceUser(token, 1);

        Assertions.assertEquals(200, response.getStatusCodeValue());

        when(userController.getReduceUser(1)).thenReturn(null);

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            backofficeController.getReduceUser(token, 1);
        });

        doThrow(ResourceNotExistException.class)
                .when(sessionManager)
                .getCurrentUser("abcde");

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            backofficeController.getReduceUser("abcde", 1);
        });
    }

    @Test
    public void getOneUserDTOTest() throws ResourceNotExistException, SQLException {
        User u = new User(1, UserType.backoffice, 37867266, "dema", "felipe", "demaria", "dema22", false, null, null);
        String token = "asfsgegegeg";
        UserResponseDTO responseDTO = new UserResponseDTO();

        when(sessionManager.getCurrentUser(token)).thenReturn(u);
        when(userController.getOneUserDTO(1)).thenReturn(responseDTO);

        ResponseEntity<UserResponseDTO> response = backofficeController.getOneUserDTO(token, 1);

        Assertions.assertEquals(200, response.getStatusCodeValue());

        doThrow(ResourceNotExistException.class)
                .when(sessionManager)
                .getCurrentUser("abcde");

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            backofficeController.getOneUserDTO("abcde", 1);
        });
    }
}
