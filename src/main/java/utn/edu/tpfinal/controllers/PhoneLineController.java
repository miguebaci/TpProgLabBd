package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.services.PhoneLineService;

import java.util.List;

@RestController
@RequestMapping("/phoneLine")

public class PhoneLineController {
    private final PhoneLineService phoneLineService;

    @Autowired
    public PhoneLineController(PhoneLineService phoneLineService) {
        this.phoneLineService = phoneLineService;
    }

    @PostMapping("/")
    public void addPhoneLine(@RequestBody PhoneLine phoneLine){
        phoneLineService.addPhoneLine(phoneLine);
    }

    @GetMapping("/")
    public List<PhoneLine> getAllPhoneLines(@RequestParam(required = false) Integer idLine){
        return phoneLineService.getAll(idLine);
    }
}
