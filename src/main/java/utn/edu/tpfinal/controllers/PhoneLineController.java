package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.services.PhoneLineService;
import utn.edu.tpfinal.session.SessionManager;

import java.util.List;
import java.util.Optional;

@Controller
public class PhoneLineController {
    private final PhoneLineService phoneLineService;
    private final SessionManager sessionManager;

    @Autowired
    public PhoneLineController(PhoneLineService phoneLineService, SessionManager sessionManager) {
        this.phoneLineService = phoneLineService;
        this.sessionManager = sessionManager;
    }

    // GET ONE PHONE LINE BY ID.
    public Optional<PhoneLine> getPhoneLine(Integer idPhoneLine) {
        return phoneLineService.getOnePhoneLine(idPhoneLine);
    }

    // GET ALL PHONE LINES.
    public List<PhoneLine> getPhoneLines() {
        return phoneLineService.getAllPhoneLines();
    }

    // POST PHONE LINE.
    public void addPhoneLine(PhoneLine newPhoneLine) {
        phoneLineService.addPhoneLine(newPhoneLine);
    }

    // DELETE ONE PHONE LINE BY ID.
    public void deletePhoneLine(Integer idPhoneLine) {
        phoneLineService.deleteOnePhoneLine(idPhoneLine);
    }

    // UPDATE PHONE LINE.
    public void updatePhoneLine(PhoneLine phoneLine, Integer idPhoneLine) {
        phoneLineService.updateOnePhoneLine(phoneLine, idPhoneLine);
    }

    public void activePhoneLine(Integer idPhoneLine) throws ResourceNotExistException {
        phoneLineService.activePhoneLine(idPhoneLine);
    }
}
