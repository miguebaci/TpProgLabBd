package utn.edu.tpfinal.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import utn.edu.tpfinal.models.Locality;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.repositories.PhoneLineRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)

public class PhoneLinesServiceTest {
    @InjectMocks
    private PhoneLineService phoneLineService;

    @Mock
    private PhoneLineRepository phoneLineRepository;

    @Test
    public void getAllPhoneLinesTest() {
        List<PhoneLine> phoneLines = new ArrayList<PhoneLine>();
        Locality locality = new Locality(351, "Carlos Paz", null, null, null);

        PhoneLine line1 = new PhoneLine(1, null, locality, null, "155210762", null);
        PhoneLine line2 = new PhoneLine(2, null, locality, null, "155210901", null);
        PhoneLine line3 = new PhoneLine(3, null, locality, null, "155210798", null);
        phoneLines.add(line1);
        phoneLines.add(line2);
        phoneLines.add(line3);

        when(phoneLineRepository.findAllPhoneLinesByPrefix(351)).thenReturn(phoneLines);
        List<PhoneLine> response = phoneLineService.getAllPhoneLinesByPrefix(351);

        assertNotNull(response);
        assertEquals(phoneLines,response);
    }
}
