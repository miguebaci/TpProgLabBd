package utn.edu.tpfinal.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.dto.UserResponseDTO;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.services.UserService;

import java.sql.SQLException;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
    @GetMapping("/user/{idUser}")
    public ResponseEntity<UserResponseDTO> getOneUserDTO (@PathVariable Integer idUser) throws SQLException {
        ResponseEntity<UserResponseDTO> responseEntity;

        // Get the dto of the user
        UserResponseDTO userResponseDTO = userService.getOneDTOUser(idUser);

        if(userResponseDTO != null) {
            responseEntity = ResponseEntity.ok(userResponseDTO);
        }else{
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return responseEntity;
    }

}
