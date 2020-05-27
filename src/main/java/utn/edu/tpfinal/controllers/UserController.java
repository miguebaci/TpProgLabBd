package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.UserType;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.services.UserService;
import utn.edu.tpfinal.services.UserTypeService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    //private final UserTypeService userTypeService;

    @Autowired
    public UserController(UserService userService/*, UserTypeService userTypeService*/) {
        this.userService = userService;
        //this.userTypeService = userTypeService;
    }

    @GetMapping("/")
    public List<User> getUsers(@RequestParam(required = false) Integer id){
        return userService.getAll(id);
    }

    @PostMapping("/")
    public void addUser(@RequestBody User newUser){
        /*//  Get the first user type we have (1 -- > employee)
        UserType userType = userTypeService.getById(1);
        newUser.setUserType(userType);*/

        // Saving the user with the user type Employee
        userService.addUser(newUser);
    }

    public User login(String username, String password) {
        if ((username != null) && (password != null)) {
            return userService.login(username, password);
        } else {
            throw new RuntimeException("username and password must have a value");
        }
    }

}
