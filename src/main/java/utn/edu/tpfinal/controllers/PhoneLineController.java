package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.services.PhoneLineService;
import utn.edu.tpfinal.session.SessionManager;

@Controller
public class PhoneLineController {
    private final PhoneLineService phoneLineService;
    private final SessionManager sessionManager;

    @Autowired
    public PhoneLineController(PhoneLineService phoneLineService, SessionManager sessionManager) {
        this.phoneLineService = phoneLineService;
        this.sessionManager = sessionManager;
    }

    // DELETE ONE PHONE LINE BY ID.
    public void deletePhoneLine(Integer idPhoneLine) {
        phoneLineService.deleteOnePhoneLine(idPhoneLine);
    }

    public void activePhoneLine(Integer idPhoneLine) throws ResourceNotExistException {
        phoneLineService.activePhoneLine(idPhoneLine);
    }
}
