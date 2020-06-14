package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.edu.tpfinal.models.UserType;
import utn.edu.tpfinal.services.UserTypeService;

import java.util.List;
import java.util.Optional;

@Controller

public class UserTypeController {
    private final UserTypeService userTypeService;

    @Autowired

    public UserTypeController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    // GET ONE USERTYPE BY ID.
    public Optional<UserType> getBill(Integer idUserType){
        return userTypeService.getOneUserType(idUserType);
    }

    // GET ALL USER TYPES.
    public List<UserType> getAllUserTypes(){
        return userTypeService.getAllUserTypes();
    }

    // POST USER TYPE.
    public void addUserType(UserType newUserType){
        userTypeService.addUserType(newUserType);
    }

    // DELETE ONE USER TYPE BY ID.
    public void deleteUserType(Integer idUserType){
        userTypeService.deleteOneUserType(idUserType);
    }

    // UPDATE USER TYPE BY ID.
    public void updateUserType(UserType userType, Integer idUserType){
        userTypeService.updateOneUserType(userType, idUserType);
    }
}
