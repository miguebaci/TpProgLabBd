package utn.edu.tpfinal.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.Exceptions.UserNotExistException;
import utn.edu.tpfinal.controllers.BillController;
import utn.edu.tpfinal.controllers.UserController;
import utn.edu.tpfinal.dto.BillForUserDTO;
import utn.edu.tpfinal.dto.CallsForUserDTO;
import utn.edu.tpfinal.dto.UserResponseDTO;
import utn.edu.tpfinal.projections.IReduceUser;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final UserController userController;
    private final BillController billController;

    @Autowired
    public ClientController(UserController userController, BillController billController) {
        this.userController = userController;
        this.billController = billController;
    }

    /*public User login(String username, String password) {
        return userController.login(username,password);
    }*/

    // GET ONE REDUCE USER BY ID.
    @GetMapping("/projection/{idUser}")
    public IReduceUser getReduceUser(@PathVariable Integer idUser){
        return userController.getReduceUser(idUser);
    }


    // Response user with DTO
    @GetMapping("/user/{idUser}")
    public ResponseEntity<UserResponseDTO> getOneUserDTO (@PathVariable Integer idUser) throws SQLException {
        return userController.getOneUserDTO(idUser);
    }
    @GetMapping("/user/bills")
    public ResponseEntity<List<BillForUserDTO>> getBillsInfoForUser(@RequestHeader("Authorization") String sessionToken,
                                                                    @RequestParam(value = "fromDate", required = false) String fromDate,
                                                                    @RequestParam(value = "toDate", required = false) String toDate) throws UserNotExistException, ParseException {
        return billController.getBillsInfoForUser(sessionToken,fromDate, toDate);
    }
}
/*
public ResponseEntity<List<CallsForUserDTO>> getBillsInfoForUser(@RequestHeader("Authorization") String sessionToken,
                                                                     @RequestParam(value = "fromDate", required = false) String fromDate,
                                                                     @RequestParam(value = "toDate", required = false) String toDate,
                                                                     @RequestParam(value = "lineNumber" ) String lineNumber,
                                                                     @RequestParam(value = "caller" ) Boolean caller) throws UserNotExistException {*/