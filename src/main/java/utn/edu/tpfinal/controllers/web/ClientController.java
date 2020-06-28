package utn.edu.tpfinal.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.Exceptions.ValidationException;
import utn.edu.tpfinal.controllers.BillController;
import utn.edu.tpfinal.controllers.CallsController;
import utn.edu.tpfinal.controllers.PhoneLineController;
import utn.edu.tpfinal.controllers.UserController;
import utn.edu.tpfinal.dto.BillForUserDTO;
import utn.edu.tpfinal.dto.CallsForUserDTO;
import utn.edu.tpfinal.dto.UserResponseDTO;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.projections.ITop10DestinationCalled;
import utn.edu.tpfinal.session.SessionManager;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final UserController userController;
    private final BillController billController;
    private final CallsController callsController;
    private final PhoneLineController phoneLineController;
    private final SessionManager sessionManager;

    @Autowired
    public ClientController(UserController userController, BillController billController, CallsController callController, PhoneLineController phoneLineController, SessionManager sessionManager) {
        this.userController = userController;
        this.billController = billController;
        this.callsController = callController;
        this.phoneLineController = phoneLineController;
        this.sessionManager = sessionManager;
    }

    // GET ONE REDUCE USER BY ID.
    @GetMapping("/profile")
    public ResponseEntity<IReduceUser> getReduceUser(@RequestHeader("Authorization") String sessionToken) throws ResourceNotExistException {
        try {
            User currentUser = sessionManager.getCurrentUser(sessionToken);
            IReduceUser userProfileDTO =  userController.getReduceUser(currentUser.getId());
            ResponseEntity<IReduceUser> responseEntity;

            if (userProfileDTO != null) {
                responseEntity = ResponseEntity.ok(userProfileDTO);
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();// q poner ?
            }

            return responseEntity;
        } catch (ResourceNotExistException e) {
            throw e;
        }
    }

    // Response user with DTO
    @GetMapping("/fullProfile")
    public ResponseEntity<UserResponseDTO> getOneUserDTO(@RequestHeader("Authorization") String sessionToken) throws ResourceNotExistException {
        try {
            User u = sessionManager.getCurrentUser(sessionToken);
            UserResponseDTO userResponseDTO =  userController.getOneUserDTO(u.getId());
            ResponseEntity<UserResponseDTO> responseEntity;

            if (userResponseDTO != null) {
                responseEntity = ResponseEntity.ok(userResponseDTO);
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return responseEntity;
        } catch (ResourceNotExistException e) {
            throw e;
        }
    }

    @GetMapping("/user/bills")
    public ResponseEntity<List<BillForUserDTO>> getBillsInfoForUser(@RequestHeader("Authorization") String sessionToken,
                                                                    @RequestParam(value = "fromDate", required = false) String fromDate,
                                                                    @RequestParam(value = "toDate", required = false) String toDate) throws ResourceNotExistException {
        try {
            User currentUser = sessionManager.getCurrentUser(sessionToken);
            List<BillForUserDTO> billForUserDTO =  billController.getBillsBetweenRangeOfDates(currentUser, fromDate, toDate);

            if (billForUserDTO.size() > 0) {
                return ResponseEntity.ok(billForUserDTO);
            } else {
                throw new ResourceNotExistException("The bill between the ranges of dates you provided has not been found");
            }
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
            User currentUser = sessionManager.getCurrentUser(sessionToken);
            List<CallsForUserDTO> callsForUserDTO = callsController.getCallsBetweenRangeOfDates(currentUser, fromDate, toDate, lineNumber, caller);

            if (callsForUserDTO.size() > 0) {
                return ResponseEntity.ok(callsForUserDTO);
            } else {
                throw new ResourceNotExistException("The calls between the ranges of dates you have provided has not been found");
            }
        } catch (ResourceNotExistException | ValidationException e) {
            throw e;
        }
    }
    
    @GetMapping("user/top10")
    public ResponseEntity<List<ITop10DestinationCalled>> getTop10DestinationCalledByUser(@RequestHeader("Authorization") String sessionToken) throws ResourceNotExistException {
        try {
            // Verify token
            User currentUser = sessionManager.getCurrentUser(sessionToken);
            List<ITop10DestinationCalled> list = callsController.getTop10DestinationWithNumberOfCalls(currentUser);

            if (list.size() > 0) {
                return ResponseEntity.ok(list);
            } else {
                throw new ResourceNotExistException("We couldnt generate the top 10 destinations called by you, because you dont have any calls yet!");
            }
        } catch (ResourceNotExistException  e) {
            throw e;
        }
    }
}