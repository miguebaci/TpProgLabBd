package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.models.Province;
import utn.edu.tpfinal.services.PhoneLineService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/phoneLine")

public class PhoneLineController {
    private final PhoneLineService phoneLineService;

    @Autowired
    public PhoneLineController(PhoneLineService phoneLineService) {
        this.phoneLineService = phoneLineService;
    }

    // GET ONE PHONE LINE BY ID.
    @GetMapping("/{idPhoneLine}")
    public Optional<PhoneLine> getPhoneLine(@PathVariable Integer idPhoneLine){
        return phoneLineService.getOnePhoneLine(idPhoneLine);
    }

    // GET ALL PHONE LINES.
    @GetMapping("/")
    public List<PhoneLine> getPhoneLines(){
        return phoneLineService.getAllPhoneLines();
    }

    // POST PHONE LINE.
    @PostMapping("/")
    public void addPhoneLine(@RequestBody PhoneLine newPhoneLine){
        phoneLineService.addPhoneLine(newPhoneLine);
    }

    // DELETE ONE PHONE LINE BY ID.
    @DeleteMapping("/{idPhoneLine}")
    public void deletePhoneLine(@PathVariable Integer idPhoneLine){
        phoneLineService.deleteOnePhoneLine(idPhoneLine);
    }

    // UPDATE PHONE LINE.
    @PutMapping("/{idPhoneLine}")
    public void updatePhoneLine(@RequestBody PhoneLine phoneLine, @PathVariable Integer idPhoneLine){
        phoneLineService.updateOnePhoneLine(phoneLine, idPhoneLine);
    }

    // GET ALL PHONE LINES WITH PREFIX : 351

    @GetMapping("/exam/{prefix}")
    public ResponseEntity<List<PhoneLine>> getAllPhoneLines(@PathVariable Integer prefix){
        ResponseEntity<List<PhoneLine>> responseEntity;
        List<PhoneLine> phoneLines = phoneLineService.getAllPhoneLinesByPrefix(prefix);

        if(phoneLines.size() > 0){
            responseEntity = ResponseEntity.ok(phoneLines);
        }else{
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return responseEntity;
    }


}
