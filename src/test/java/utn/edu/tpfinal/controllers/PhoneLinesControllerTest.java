package utn.edu.tpfinal.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import utn.edu.tpfinal.models.Locality;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.models.Province;
import utn.edu.tpfinal.services.PhoneLineService;
import utn.edu.tpfinal.services.ProvinceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)

public class PhoneLinesControllerTest {
    @InjectMocks
    private PhoneLineController phoneLineController;
    @Mock
    private PhoneLineService phoneLineService;

    @Test
    public void getAllPhoneLinesTest(){
        List<PhoneLine> phoneLines = new ArrayList<PhoneLine>();
        Locality locality = new Locality(351, "Carlos Paz", null, null, null);

        PhoneLine line1 = new PhoneLine(1, null, locality, null, "155210762", null);
        PhoneLine line2 = new PhoneLine(2, null, locality, null, "155210901", null);
        PhoneLine line3 = new PhoneLine(3, null, locality, null, "155210798", null);
        phoneLines.add(line1);
        phoneLines.add(line2);
        phoneLines.add(line3);

        when(phoneLineService.getAllPhoneLinesByPrefix(locality.getPrefix())).thenReturn(phoneLines);
        ResponseEntity<List<PhoneLine>> responsePhoneLine = phoneLineController.getAllPhoneLines(locality.getPrefix());

        assertEquals(200, responsePhoneLine.getStatusCodeValue());
    }
}
