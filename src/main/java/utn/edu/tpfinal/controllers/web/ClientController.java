package utn.edu.tpfinal.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.Exceptions.ValidationException;
import utn.edu.tpfinal.controllers.BillController;
import utn.edu.tpfinal.controllers.CallsController;
import utn.edu.tpfinal.controllers.UserController;
import utn.edu.tpfinal.dto.BillForUserDTO;
import utn.edu.tpfinal.dto.CallsForUserDTO;
import utn.edu.tpfinal.dto.UserResponseDTO;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.session.SessionManager;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final UserController userController;
    private final BillController billController;
    private final CallsController callsController;
    private final SessionManager sessionManager;

    @Autowired
    public ClientController(UserController userController, BillController billController, CallsController callController, SessionManager sessionManager) {
        this.userController = userController;
        this.billController = billController;
        this.callsController = callController;
        this.sessionManager = sessionManager;
    }

    // GET ONE REDUCE USER BY ID.
    @GetMapping("/projection/{idUser}")
    public IReduceUser getReduceUser(@PathVariable Integer idUser) {
        return userController.getReduceUser(idUser);
    }

    // Response user with DTO
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> getOneUserDTO(@RequestHeader("Authorization") String sessionToken) throws ResourceNotExistException {
        try {
            User u = sessionManager.getCurrentUser(sessionToken);
            return userController.getOneUserDTO(u.getId());
        } catch (ResourceNotExistException e) {
            throw e;
        }
    }

    @GetMapping("/user/bills")
    public ResponseEntity<List<BillForUserDTO>> getBillsInfoForUser(@RequestHeader("Authorization") String sessionToken,
                                                                    @RequestParam(value = "fromDate", required = false) String fromDate,
                                                                    @RequestParam(value = "toDate", required = false) String toDate) throws ResourceNotExistException {
        try {
            return billController.getBillsBetweenRangeOfDates(sessionToken, fromDate, toDate);
        } catch (ResourceNotExistException e) {
            throw e;
        }
    }

    @GetMapping("/user/calls")
    public ResponseEntity<List<CallsForUserDTO>> getCallsInfoForUser(@RequestHeader("Authorization") String sessionToken,
                                                                     @RequestParam(value = "fromDate", required = false) String fromDate,
                                                                     @RequestParam(value = "toDate", required = false) String toDate,
                                                                     @RequestParam(value = "lineNumber") String lineNumber,
                                                                     @RequestParam(value = "caller") Boolean caller) throws ResourceNotExistException, ValidationException {
        try {
            return callsController.getCallsBetweenRangeOfDates(sessionToken, fromDate, toDate,lineNumber,caller);
        } catch (ResourceNotExistException | ValidationException e) {
            throw e;
        }
    }

}