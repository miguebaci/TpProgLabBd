package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.Exceptions.ValidationException;
import utn.edu.tpfinal.dto.CallsForUserDTO;
import utn.edu.tpfinal.models.Call;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.projections.ITop10DestinationCalled;
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
    public Call addCall(CallsForUserDTO newCall) throws ResourceNotExistException {
        return callsService.addCall(newCall);
    }

    // Get all calls between two ranges of dates
    public List<CallsForUserDTO> getCallsByUser(Integer idUser, String lineNumber) throws ResourceNotExistException {
        try {
            PhoneLine line = phoneLineService.getOnePhoneLineByUser(lineNumber, idUser);

            if (line != null) {

                List<CallsForUserDTO> callsForUserDTO;
                callsForUserDTO = callsService.getCallsForUserDTO(lineNumber, true);
                return callsForUserDTO;
            } else {
                throw new ResourceNotExistException("This user does not owns this line");
            }
        } catch (ResourceNotExistException e) {
            throw e;
        }
    }

    // Get all calls between two ranges of dates
    public List<CallsForUserDTO> getCallsBetweenRangeOfDates(User currentUser, String fromDate, String toDate, String lineNumber, Boolean caller) throws ValidationException {
        try {
            // Check if the current log user its the owner of the line ( in the case they have access to a valid token and they want to view information that they do not have access)
            if (phoneLineService.getOnePhoneLineByUser(lineNumber, currentUser.getId()) == null) {
                throw new ValidationException("You can view your calls because you dont own this phone line. Please verify your phone line number.");
            }

            List<CallsForUserDTO> callsForUserDTO;

            if (fromDate != null && toDate != null) {
                callsForUserDTO = callsService.getCallsBetweenRange(fromDate, toDate, lineNumber, caller);
            } else {
                // we return all calls for the line
                callsForUserDTO = callsService.getCallsForUserDTO(lineNumber, caller);
            }

            return callsForUserDTO;
        } catch (ValidationException e) {
            throw e;
        }
    }

    // Get top 10 destination most called
    @GetMapping("/Top10DestinationCalled")
    public List<ITop10DestinationCalled> getTop10DestinationWithNumberOfCalls(User currentUser)  {
        return callsService.getTop10DestinationsWithNumberOfCalls(currentUser.getId());
    }
}
