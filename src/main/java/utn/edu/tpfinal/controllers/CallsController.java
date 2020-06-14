package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.Exceptions.UserNotExistException;
import utn.edu.tpfinal.dto.CallsForUserDTO;
import utn.edu.tpfinal.models.Call;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.services.CallsService;
import utn.edu.tpfinal.session.SessionManager;

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
    public void addCall(Call newCall){
        callsService.addCall(newCall);
    }

    // DELETE ONE CALL BY ID.
    public void deleteCall(Integer idCall){
        callsService.deleteOneCall(idCall);
    }

    // UPDATE CALL BY ID.
    public void updateCall(Call call, Integer idCall){
        callsService.updateOneCall(call, idCall);
    }

    // Get all calls between two ranges of dates
    public ResponseEntity<List<CallsForUserDTO>> getBillsInfoForUser(@RequestHeader("Authorization") String sessionToken,
                                                                     @RequestParam(value = "fromDate", required = false) String fromDate,
                                                                     @RequestParam(value = "toDate", required = false) String toDate,
                                                                     @RequestParam(value = "lineNumber" ) String lineNumber,
                                                                     @RequestParam(value = "caller" ) Boolean caller) throws UserNotExistException {
        User currentUser = getCurrentUser(sessionToken);

        List<CallsForUserDTO> callsForUserDTO;

        if(fromDate != null && toDate != null){
            callsForUserDTO= callsService.geCallsBetweenRange(fromDate,toDate,lineNumber,caller);
        }else{
            // we return all calls for the line
            callsForUserDTO = callsService.getAllCallsForUserDTO(lineNumber,caller);
        }

        return (callsForUserDTO.size() > 0) ? ResponseEntity.ok(callsForUserDTO) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private User getCurrentUser(String sessionToken) throws UserNotExistException {
        //throw new UserNotexistException();
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(UserNotExistException::new);
    }

}
