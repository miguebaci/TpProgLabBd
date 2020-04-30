package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public List<User> getUsers(@RequestParam(required = false) Integer dni){
        return userService.getAll(dni);
    }

    @PostMapping("/")
    public void addUser(@RequestBody User newUser){
        userService.addUser(newUser);
    }


}
