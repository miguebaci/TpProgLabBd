package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.Users;
import utn.edu.tpfinal.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public List<Users> getUsers(@RequestParam(required = false) Integer dni){
        return userService.getAll(dni);
    }

    @PostMapping("/")
    public void addUser(@RequestBody Users newUsers){
        userService.addUser(newUsers);
    }


}
