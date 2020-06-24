package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.Exceptions.ValidationException;
import utn.edu.tpfinal.dto.UserResponseDTO;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.services.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET ONE USER BY ID.
    public Optional<User> getUser(Integer idUser) {
        return userService.getOneUser(idUser);
    }

    // GET ALL USERS.
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    // POST USER.
    public ResponseEntity<User> addUser(User newUser) throws ResourceNotExistException, NoSuchAlgorithmException {
        return userService.addUser(newUser);
    }

    // DELETE ONE USER BY ID.
    public void deleteUser(Integer idUser) {
        userService.deleteOneUser(idUser);
    }

    // UPDATE USER.
    public void updateUser(User user, Integer idUser) throws NoSuchAlgorithmException, ResourceNotExistException {
        userService.updateOneUser(user, idUser);
    }

    public User login(String username, String password) throws NoSuchAlgorithmException, ResourceNotExistException, ValidationException {
        /*if ((username != null) && (password != null)) {
            return userService.login(username, password);
        } else {
            throw new RuntimeException("username and password must have a value");
        }*/
        User u = null;

        try {
            if ((username != null) && (password != null)) {
                u = userService.login(username, password);
            } else {
                throw new ValidationException("You must introduce username and password so you can log in.");
            }
        } catch (NoSuchAlgorithmException | ResourceNotExistException | ValidationException e) {
            throw e;
        }

        return u;
    }

    public IReduceUser getReduceUser(Integer idUser) {
        return userService.getOneReduceUser(idUser);
    }

    public ResponseEntity<UserResponseDTO> getOneUserDTO(Integer idUser) {
        ResponseEntity<UserResponseDTO> responseEntity;

        // Get the dto of the user
        UserResponseDTO userResponseDTO = userService.getOneDTOUser(idUser);

        if (userResponseDTO != null) {
            responseEntity = ResponseEntity.ok(userResponseDTO);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return responseEntity;
    }

    public void activeUser(Integer idUser) throws NoSuchAlgorithmException, ResourceNotExistException {
        userService.activeUser(idUser);
    }
}