package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.dto.UserRespondeDTO;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.services.UserService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET ONE USER BY ID.
    @GetMapping("/{idUser}")
    public Optional<User> getUser(@PathVariable Integer idUser){
        return userService.getOneUser(idUser);
    }

    // GET ALL USERS.
    @GetMapping("/")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    // POST USER.
    @PostMapping("/")
    public void addUser(@RequestBody User newUser){
        userService.addUser(newUser);
    }

    // DELETE ONE USER BY ID.
    @DeleteMapping("/{idUser}")
    public void deleteUser(@PathVariable Integer idUser){
        userService.deleteOneUser(idUser);
    }

    // UPDATE USER.
    @PutMapping("/{idUser}")
    public void updateUser(@RequestBody User user, @PathVariable Integer idUser){
        userService.updateOneUser(user, idUser);
    }

    public User login(String username, String password) {
        if ((username != null) && (password != null)) {
            return userService.login(username, password);
        } else {
            throw new RuntimeException("username and password must have a value");
        }
    }


    // GET ONE REDUCE USER BY ID.
    @GetMapping("/projection/{idUser}")
    public IReduceUser getReduceUser(@PathVariable Integer idUser){
        return userService.getOneReduceUser(idUser);
    }


    // Response user with DTO
    @GetMapping("/reduce/{idUser}")
    public ResponseEntity<UserRespondeDTO> getOneUserDTO (@PathVariable Integer idUser) throws SQLException {
        ResponseEntity<UserRespondeDTO> responseEntity;

        // Get the dto of the user
        UserRespondeDTO userRespondeDTO = userService.getOneDTOUser(idUser);

        if(userRespondeDTO != null) {
            responseEntity = ResponseEntity.ok(userRespondeDTO);
        }else{
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return responseEntity;
    }

}
