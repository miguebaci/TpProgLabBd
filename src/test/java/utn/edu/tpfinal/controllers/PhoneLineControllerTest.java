package utn.edu.tpfinal.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.models.LineType;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.models.UserType;
import utn.edu.tpfinal.services.PhoneLineService;
import utn.edu.tpfinal.session.SessionManager;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class PhoneLineControllerTest {
    @InjectMocks
    private PhoneLineController phoneLineController;

    @Mock
    private PhoneLineService phoneLineService;

    @Mock
    private SessionManager sessionManager;

    @Test
    public void activePhoneLineTest () throws ResourceNotExistException {
        PhoneLine phoneLineToUpdate = new PhoneLine(1, null, null, LineType.mobile, "223155123456", false, null);
        Mockito.doNothing().when(phoneLineService).activePhoneLine(phoneLineToUpdate.getIdLine());
        phoneLineController.activePhoneLine(phoneLineToUpdate.getIdLine());
        Mockito.verify(phoneLineService, Mockito.times(1)).activePhoneLine(phoneLineToUpdate.getIdLine());
    }
}

