package utn.edu.tpfinal.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.Exceptions.LogOutException;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.Exceptions.ValidationException;
import utn.edu.tpfinal.controllers.UserController;
import utn.edu.tpfinal.dto.LoginRequestDto;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.session.SessionManager;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/")
public class LoginController {

    UserController userController;
    SessionManager sessionManager;

    @Autowired
    public LoginController(UserController userController, SessionManager sessionManager) {
        this.userController = userController;
        this.sessionManager = sessionManager;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto) throws NoSuchAlgorithmException, ResourceNotExistException, ValidationException {
        ResponseEntity response;
        try {
            User u = userController.login(loginRequestDto.getUsername(), loginRequestDto.getPass());
            String token = sessionManager.createSession(u);
            response = ResponseEntity.ok().headers(createHeaders(token)).build();
        } catch (NoSuchAlgorithmException | ResourceNotExistException | ValidationException e) {
            throw e;
        }
        return response;
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) throws ValidationException, ResourceNotExistException {
        // Check for null in token
        if (token == "") {
            throw new ValidationException("You must provided a valid token authorization to log out.");
        }

        // Then we get the current user with the token, or we throw error if the user does not exist.
        try {
            User u = sessionManager.getCurrentUser(token);
            sessionManager.removeSession(token);
            return ResponseEntity.ok().build();
        } catch (ResourceNotExistException e) {
            throw e;
        }
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        return responseHeaders;
    }
}