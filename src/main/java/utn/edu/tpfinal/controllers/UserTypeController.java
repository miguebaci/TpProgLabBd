package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.UserType;
import utn.edu.tpfinal.services.UserTypeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/userTypes")

public class UserTypeController {
    private final UserTypeService userTypeService;

    @Autowired

    public UserTypeController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    // GET ONE USERTYPE BY ID.
    @GetMapping("/{idUserType}")
    public Optional<UserType> getBill(@PathVariable Integer idUserType){
        return userTypeService.getOneUserType(idUserType);
    }

    // GET ALL USER TYPES.
    @GetMapping("/")
    public List<UserType> getAllUserTypes(){
        return userTypeService.getAllUserTypes();
    }

    // POST USER TYPE.
    @PostMapping("/")
    public void addUserType(@RequestBody UserType newUserType){
        userTypeService.addUserType(newUserType);
    }

    // DELETE ONE USER TYPE BY ID.
    @DeleteMapping("/{idUserType}")
    public void deleteUserType(@PathVariable Integer idUserType){
        userTypeService.deleteOneUserType(idUserType);
    }

    // UPDATE USER TYPE BY ID.
    @PutMapping("/{idUserType}")
    public void updateUserType(@RequestBody UserType userType, @PathVariable Integer idUserType){
        userTypeService.updateOneUserType(userType, idUserType);
    }
}
