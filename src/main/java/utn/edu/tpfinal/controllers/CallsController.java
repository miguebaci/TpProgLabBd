package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.Exceptions.UserNotexistException;
import utn.edu.tpfinal.dto.BillForUserDTO;
import utn.edu.tpfinal.dto.CallForUserDTO;
import utn.edu.tpfinal.models.Call;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.services.CallsService;
import utn.edu.tpfinal.session.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/calls")

public class CallsController {
    private final CallsService callsService;
    private final SessionManager sessionManager;

    @Autowired
    public CallsController(CallsService callsService, SessionManager sessionManager) {
        this.callsService = callsService;
        this.sessionManager = sessionManager;
    }

    // GET ONE CALL BY ID.
    @GetMapping("/{idCall}")
    public Optional<Call> getCall(@PathVariable Integer idCall){
        return callsService.getOneCall(idCall);
    }

    // GET ALL CALLS.
    @GetMapping("/")
    public List<Call> getCalls(){
        return callsService.getAllCalls();
    }

    // POST CALL.
    @PostMapping("/")
    public void addCall(@RequestBody Call newCall){
        callsService.addCall(newCall);
    }

    // DELETE ONE CALL BY ID.
    @DeleteMapping("/{idCall}")
    public void deleteCall(@PathVariable Integer idCall){
        callsService.deleteOneCall(idCall);
    }

    // UPDATE CALL BY ID.
    @PutMapping("/{idCall}")
    public void updateCall(@RequestBody Call call, @PathVariable Integer idCall){
        callsService.updateOneCall(call, idCall);
    }

    // Get all calls between two ranges of dates
    @GetMapping("/userCallInfo")
    public ResponseEntity<List<CallForUserDTO>> getBillsInfoForUser(@RequestHeader("Authorization") String sessionToken,
                                                                    @RequestParam(value = "fromDate", required = false) String fromDate,
                                                                    @RequestParam(value = "toDate", required = false) String toDate) throws UserNotexistException, ParseException {
        User currentUser = getCurrentUser(sessionToken);
        List<CallForUserDTO> callsForUserDTO;

        if(fromDate != null && toDate != null){
            // We search calls between the dates, first we convert the string to local date time
            Date from = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(fromDate);
            Date to = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(toDate);
            callsForUserDTO= callsService.geCallsBetweenRange(currentUser.getId(),from,to);
        }else{
            // we return all bills
            callsForUserDTO = callsService.getAllCallsForUserDTO();
        }

        return (callsForUserDTO.size() > 0) ? ResponseEntity.ok(callsForUserDTO) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private User getCurrentUser(String sessionToken) throws UserNotexistException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(UserNotexistException::new);
    }

}
