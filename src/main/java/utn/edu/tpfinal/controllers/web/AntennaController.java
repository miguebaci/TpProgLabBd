package utn.edu.tpfinal.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.controllers.CallsController;
import utn.edu.tpfinal.dto.CallsForUserDTO;
import utn.edu.tpfinal.models.Call;

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
    public ResponseEntity<Call> makeCall(@RequestHeader("Authorization") String sessionToken, @RequestBody CallsForUserDTO newCall) throws ResourceNotExistException {
        ResponseEntity response;
        URI location = getLocation(this.callsController.addCall(newCall).getBody());
        response = ResponseEntity.created(location).build();
        return response;
    }

    private URI getLocation(Call call) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(call.getIdCall())
                .toUri();
    }
}
