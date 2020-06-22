package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import utn.edu.tpfinal.Exceptions.PhoneLineNotFoundException;
import utn.edu.tpfinal.Exceptions.UserNotExistException;
import utn.edu.tpfinal.dto.CallsForUserDTO;
import utn.edu.tpfinal.models.Call;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.services.CallsService;
import utn.edu.tpfinal.session.SessionManager;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller

public class CallsController {
    private final CallsService callsService;
    private final SessionManager sessionManager;

    @Autowired
    public CallsController(CallsService callsService, SessionManager sessionManager) {
        this.callsService = callsService;
        this.sessionManager = sessionManager;
    }

    // GET ONE CALL BY ID.
    public Optional<Call> getCall(Integer idCall){
        return callsService.getOneCall(idCall);
    }

    // GET ALL CALLS.
    public List<Call> getCalls(){
        return callsService.getAllCalls();
    }

    // POST CALL.
    public URI addCall(CallsForUserDTO newCall) throws PhoneLineNotFoundException {
        return callsService.addCall(newCall);
    }

    // Get all calls between two ranges of dates
    public ResponseEntity<List<CallsForUserDTO>> getBillsInfoForUser(String sessionToken, String fromDate, String toDate, String lineNumber, Boolean caller) throws UserNotExistException {
        User currentUser = getCurrentUser(sessionToken);

        List<CallsForUserDTO> callsForUserDTO;

        if(fromDate != null && toDate != null){
            callsForUserDTO= callsService.getCallsBetweenRange(fromDate,toDate,lineNumber,caller);
        }else{
            // we return all calls for the line
            callsForUserDTO = callsService.getAllCallsForUserDTO(lineNumber,caller);
        }

        return (callsForUserDTO.size() > 0) ? ResponseEntity.ok(callsForUserDTO) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private User getCurrentUser(String sessionToken) throws UserNotExistException {
        //throw new UserNotExistException();
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(UserNotExistException::new);
    }

}
