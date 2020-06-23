package utn.edu.tpfinal.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.controllers.PhoneLineController;
import utn.edu.tpfinal.controllers.UserController;
import utn.edu.tpfinal.dto.UserResponseDTO;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.services.UserService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/backoffice")
public class BackofficeController {

    private final UserController userController;
    private final PhoneLineController phoneLineController;

    @Autowired
    public BackofficeController(UserController userController,PhoneLineController phoneLineController) {
        this.userController = userController;
        this.phoneLineController = phoneLineController;
    }

    // GET ONE USER BY ID.
    @GetMapping("/{idUser}")
    public Optional<User> getUser(@PathVariable Integer idUser){
        return userController.getUser(idUser);
    }

    // GET ALL USERS.
    @GetMapping("/")
    public List<User> getUsers(){
        return userController.getUsers();
    }

    // POST USER.
    @PostMapping("/")
    public void addUser(@RequestBody User newUser) throws NoSuchAlgorithmException {
        userController.addUser(newUser);
    }

    // DELETE ONE USER BY ID.
    @DeleteMapping("/{idUser}")
    public void deleteUser(@PathVariable Integer idUser){
        userController.deleteUser(idUser);
    }


    // SUSPEND OR REACTIVATE USER.
    @PutMapping("/active/users/{idUser}")
    public void suspendUser(@PathVariable Integer idUser) throws NoSuchAlgorithmException {
        userController.activeUser(idUser);
    }

    // SUSPEND OR REACTIVATE PHONELINE.
    @PutMapping("/active/phoneline/{idUser}")
    public void suspendphoneline(@PathVariable Integer idPhone) throws NoSuchAlgorithmException {
        phoneLineController.activePhoneLine(idPhone);
    }

    // UPDATE USER.
    @PutMapping("/{idUser}")
    public void updateUser(@RequestBody User user, @PathVariable Integer idUser) throws NoSuchAlgorithmException {
        userController.updateUser(user, idUser);
    }

    // GET ONE REDUCE USER BY ID.
    @GetMapping("/projection/{idUser}")
    public IReduceUser getReduceUser(@PathVariable Integer idUser){
        return userController.getReduceUser(idUser);
    }


    // Response user with DTO
    @GetMapping("/reduce/{idUser}")
    public ResponseEntity<UserResponseDTO> getOneUserDTO (@PathVariable Integer idUser) throws SQLException {
        ResponseEntity<UserResponseDTO> responseEntity;

        // Get the dto of the user
        responseEntity = userController.getOneUserDTO(idUser);

        return responseEntity;
    }

}
