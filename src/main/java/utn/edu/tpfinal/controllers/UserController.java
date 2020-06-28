package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.edu.tpfinal.Exceptions.ResourceAlreadyExistException;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.Exceptions.ValidationException;
import utn.edu.tpfinal.dto.UserResponseDTO;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.services.UserService;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.NoSuchElementException;
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
    public List<IReduceUser> getUsers() {
        return userService.getAllUsers();
    }

    // POST USER.
    public User addUser(User newUser) throws ResourceAlreadyExistException, NoSuchAlgorithmException {
        return userService.addUser(newUser);
    }

    // DELETE ONE USER BY ID.
    public void deleteUser(Integer idUser) throws ResourceNotExistException {
        try {
            userService.deleteOneUser(idUser);
        }catch(NoSuchElementException e){
            throw new ResourceNotExistException("The user id you want to delete does not exist.");
        }
    }

    // UPDATE USER.
    public void updateUser(User user, Integer idUser) throws NoSuchAlgorithmException, ResourceNotExistException {
        userService.updateOneUser(user, idUser);
    }

    public User login(String username, String password) throws NoSuchAlgorithmException, ResourceNotExistException, ValidationException {
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

    public UserResponseDTO getOneUserDTO(Integer idUser) throws ResourceNotExistException {
        return userService.getOneDTOUser(idUser);
    }

    public void activeUser(Integer idUser) throws ResourceNotExistException {
        userService.activeUser(idUser);
    }
}