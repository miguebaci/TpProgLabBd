package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.models.UserType;
import utn.edu.tpfinal.services.UserTypeService;

import java.util.List;

@RestController
@RequestMapping("/userTypes")

public class UserTypeController {
    private final UserTypeService userTypeService;

    @Autowired

    public UserTypeController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    @GetMapping("/")
    public List<UserType> getUserTypes(@RequestParam(required = false) String userTypeName){
        return userTypeService.getAll(userTypeName);
    }

    @PostMapping("/")
    public void addUserTypes(@RequestBody UserType newUserType){
        // Saving the user with the user type Employee
        userTypeService.addUserType(newUserType);
    }

}
