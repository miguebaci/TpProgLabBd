package utn.edu.tpfinal.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.controllers.PhoneLineController;
import utn.edu.tpfinal.controllers.UserController;
import utn.edu.tpfinal.dto.UserResponseDTO;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.projections.IReduceUser;

import java.net.URI;
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
    public Optional<User> getUser(@RequestHeader("Authorization") String sessionToken, @PathVariable Integer idUser){
        return userController.getUser(idUser);
    }

    // GET ALL USERS.
    @GetMapping("/")
    public List<User> getUsers(@RequestHeader("Authorization") String sessionToken){
        return userController.getUsers();
    }

    // POST USER.
    @PostMapping("/")
    public ResponseEntity<User> addUser(@RequestHeader("Authorization") String sessionToken, @RequestBody User newUser) throws  NoSuchAlgorithmException {
        ResponseEntity response;
        try{
            URI location = getLocation(this.userController.addUser(newUser).getBody());
            response = ResponseEntity.created(location).build();
        }catch (NoSuchAlgorithmException | ResourceNotExistException e){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return response;
    }

    // DELETE ONE USER BY ID.
    @DeleteMapping("/{idUser}")
    public void deleteUser(@RequestHeader("Authorization") String sessionToken, @PathVariable Integer idUser){
        userController.deleteUser(idUser);
    }


    // SUSPEND OR REACTIVATE USER.
    @PutMapping("/active/users/{idUser}")
    public void suspendUser(@RequestHeader("Authorization") String sessionToken, @PathVariable Integer idUser) throws NoSuchAlgorithmException, ResourceNotExistException {
        userController.activeUser(idUser);
    }

    // SUSPEND OR REACTIVATE PHONELINE.
    @PutMapping("/active/phoneline/{idUser}")
    public void suspendphoneline(@RequestHeader("Authorization") String sessionToken, @PathVariable Integer idPhone) throws NoSuchAlgorithmException {
        phoneLineController.activePhoneLine(idPhone);
    }

    // UPDATE USER.
    @PutMapping("/{idUser}")
    public void updateUser(@RequestHeader("Authorization") String sessionToken, @RequestBody User user, @PathVariable Integer idUser) throws NoSuchAlgorithmException, ResourceNotExistException {
        userController.updateUser(user, idUser);
    }

    // GET ONE REDUCE USER BY ID.
    @GetMapping("/projection/{idUser}")
    public IReduceUser getReduceUser(@RequestHeader("Authorization") String sessionToken, @PathVariable Integer idUser){
        return userController.getReduceUser(idUser);
    }


    // Response user with DTO
    @GetMapping("/reduce/{idUser}")
    public ResponseEntity<UserResponseDTO> getOneUserDTO (@RequestHeader("Authorization") String sessionToken, @PathVariable Integer idUser) throws SQLException {
        ResponseEntity<UserResponseDTO> responseEntity;

        // Get the dto of the user
        responseEntity = userController.getOneUserDTO(idUser);

        return responseEntity;
    }

    private URI getLocation(User user) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
    }

}
