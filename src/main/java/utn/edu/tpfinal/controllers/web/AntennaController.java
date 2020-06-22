package utn.edu.tpfinal.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utn.edu.tpfinal.Exceptions.PhoneLineNotFoundException;
import utn.edu.tpfinal.controllers.CallsController;
import utn.edu.tpfinal.dto.CallsForUserDTO;

import java.net.URI;

@RestController
@RequestMapping("/antenna")
public class AntennaController {

    private final CallsController callsController;

    @Autowired
    public AntennaController(CallsController callsController) {
        this.callsController = callsController;
    }

    // POST CALL.
    @PostMapping("/calls")
    public ResponseEntity<CallsForUserDTO> makeCall(@RequestBody CallsForUserDTO newCall) throws PhoneLineNotFoundException {
        ResponseEntity response;
        try{
            URI location =this.callsController.addCall(newCall);
            response = ResponseEntity.created(location).build();
        }catch (PhoneLineNotFoundException e){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return response;
    }

}
