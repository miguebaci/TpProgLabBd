package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.Call;
import utn.edu.tpfinal.services.CallsService;

import java.util.List;

@RestController
@RequestMapping("/calls")

public class CallsController {
    private final CallsService callsService;

    @Autowired
    public CallsController(CallsService callsService) {
        this.callsService = callsService;
    }

    @PostMapping("/")
    public void addCalls(@RequestBody Call call){
        callsService.addCall(call);
    }

    @GetMapping("/")
    public List<Call> getAllCalls(@RequestParam(required = false) Integer idCall){
        return callsService.getAll(idCall);
    }
}
