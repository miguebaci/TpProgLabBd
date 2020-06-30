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
import utn.edu.tpfinal.Exceptions.LogOutException;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.Exceptions.ValidationException;
import utn.edu.tpfinal.controllers.UserController;
import utn.edu.tpfinal.dto.LoginRequestDto;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.models.UserType;
import utn.edu.tpfinal.session.SessionManager;

import java.security.NoSuchAlgorithmException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;
    @Mock
    private UserController userController;
    @Mock
    private SessionManager sessionManager;

    @Test
    public void loginTest() throws ResourceNotExistException, ValidationException, NoSuchAlgorithmException {
        User u = new User(1, UserType.client, 37867266, "dema", "felipe", "demaria", "dema22", false, null, null);
        String token = "asfsgegegeg";
        LoginRequestDto loginRequestDto = new LoginRequestDto("Miguebaci", "123456");

        when(sessionManager.getCurrentUser(token)).thenReturn(u);
        when(userController.login(loginRequestDto.getUsername(), loginRequestDto.getPass())).thenReturn(u);
        ResponseEntity response = loginController.login(loginRequestDto);

        Assertions.assertEquals(200, response.getStatusCodeValue());

        doThrow(ResourceNotExistException.class)
                .when(sessionManager)
                .getCurrentUser("asdad");

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            when(sessionManager.getCurrentUser("asdad")).thenReturn(u);
        });
    }

    @Test
    public void logoutTest() throws ValidationException, LogOutException, ResourceNotExistException {
        String token = "";
        User u = new User(1, UserType.client, 37867266, "dema", "felipe", "demaria", "dema22", false, null, null);

        Assertions.assertThrows(ValidationException.class, () -> {
            loginController.logout(token);
        });

        String token2 = "kjdajksd";
        when(sessionManager.getCurrentUser(token)).thenReturn(u);
        doNothing().when(sessionManager).removeSession(token);

        ResponseEntity response = loginController.logout(token2);

        Assertions.assertEquals(200, response.getStatusCodeValue());

        doThrow(ResourceNotExistException.class)
                .when(sessionManager)
                .getCurrentUser("asdad");

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            when(sessionManager.getCurrentUser("asdad")).thenReturn(u);
        });
    }
}
