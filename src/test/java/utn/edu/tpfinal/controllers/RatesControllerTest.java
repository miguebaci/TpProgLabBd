package utn.edu.tpfinal.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.models.Locality;
import utn.edu.tpfinal.models.Province;
import utn.edu.tpfinal.models.Rate;
import utn.edu.tpfinal.services.RateService;

import java.sql.Date;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class RatesControllerTest {
    @InjectMocks
    private RatesController ratesController;

    @Mock
    private RateService rateService;

    @Test
    public void getRatesByLocalityTest() throws ResourceNotExistException {
        Province p = new Province(1, "Buenos Aires",null);
        Locality l = new Locality(223, "Mar del Plata", p, null, null);
        Locality l2 = new Locality(226, "Miramar", p, null, null);
        Rate r = new Rate(null, l, l2, (float) 12, Date.valueOf("2020-01-10"), Date.valueOf("2020-02-10"), (float)5, null);
        Mockito.when(rateService.getRatesByLocality(l.getPrefix(),l2.getPrefix())).thenReturn(r);
        Rate response =  ratesController.getRatesByLocality(l.getPrefix(),l2.getPrefix());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(r,response);
    }
}
