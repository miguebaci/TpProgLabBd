package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.Exceptions.ValidationException;
import utn.edu.tpfinal.dto.CallsForUserDTO;
import utn.edu.tpfinal.models.Call;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.services.CallsService;
import utn.edu.tpfinal.services.PhoneLineService;
import utn.edu.tpfinal.services.UserService;
import utn.edu.tpfinal.session.SessionManager;

import java.util.List;
import java.util.Optional;

@Controller

public class CallsController {
    private final CallsService callsService;
    private final SessionManager sessionManager;
    private final PhoneLineService phoneLineService;
    private final UserService userService;

    @Autowired
    public CallsController(CallsService callsService, SessionManager sessionManager, PhoneLineService phoneLineService, UserService userService) {
        this.callsService = callsService;
        this.sessionManager = sessionManager;
        this.phoneLineService = phoneLineService;
        this.userService = userService;
    }

    // GET ONE CALL BY ID.
    public Optional<Call> getCall(Integer idCall) {
        return callsService.getOneCall(idCall);
    }

    // GET ALL CALLS.
    public List<Call> getCalls() {
        return callsService.getAllCalls();
    }

    // POST CALL.
    public ResponseEntity<Call> addCall(CallsForUserDTO newCall) throws ResourceNotExistException {
        return callsService.addCall(newCall);
    }

    // Get all calls between two ranges of dates
    public ResponseEntity<List<CallsForUserDTO>> getCallsByUser(Integer IdUser, String lineNumber) throws ResourceNotExistException{
        try {
            User currentUser = userService.getOneUser(IdUser).get();
            PhoneLine line = phoneLineService.getByLineNumber(lineNumber);

            if(currentUser.getPhoneLines().contains(line)) {

                List<CallsForUserDTO> callsForUserDTO;
                callsForUserDTO = callsService.getCallsForUserDTO(lineNumber, true);
                if (callsForUserDTO.size() > 0) {
                    return ResponseEntity.ok(callsForUserDTO);
                } else {
                    throw new ResourceNotExistException("The calls between the ranges of dates you have provided has not been found");
                }
            }
            else{
                throw new ResourceNotExistException("This user does not owns this line");
            }
        } catch (ResourceNotExistException e) {
            throw e;
        }
    }

    // Get all calls between two ranges of dates
    public ResponseEntity<List<CallsForUserDTO>> getCallsBetweenRangeOfDates(String sessionToken, String fromDate, String toDate, String lineNumber, Boolean caller) throws ResourceNotExistException, ValidationException {
        try {
            User currentUser = sessionManager.getCurrentUser(sessionToken);

            // Check if the current log user its the owner of the line ( in the case they have access to a valid token and they want to view information that they do not have access)
            if (phoneLineService.getOnePhoneLineByUser(lineNumber, currentUser.getId()) == null) {
                throw new ValidationException("You can view your calls because you dont own this phone line. Please verify your phone line number.");
            }

            List<CallsForUserDTO> callsForUserDTO;

            if (fromDate != null && toDate != null) {
                callsForUserDTO = callsService.geCallsBetweenRange(fromDate, toDate, lineNumber, caller);
            } else {
                // we return all calls for the line
                callsForUserDTO = callsService.getCallsForUserDTO(lineNumber, caller);
            }

            if (callsForUserDTO.size() > 0) {
                return ResponseEntity.ok(callsForUserDTO);
            } else {
                throw new ResourceNotExistException("The calls between the ranges of dates you have provided has not been found");
            }
        } catch (ResourceNotExistException | ValidationException e) {
            throw e;
        }
    }
}
