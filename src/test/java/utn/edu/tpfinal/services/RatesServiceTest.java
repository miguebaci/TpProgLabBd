package utn.edu.tpfinal.services;

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
import utn.edu.tpfinal.models.Rate;
import utn.edu.tpfinal.repositories.RatesRepository;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class RatesServiceTest {
    @InjectMocks
    private RatesService ratesService;
    @Mock
    private RatesRepository ratesRepository;

    @Test
    public void getOneRateTest() {
        Rate rate = new Rate();
        Mockito.when(ratesRepository.findById(1)).thenReturn(Optional.of(rate));
        Optional<Rate> response = ratesService.getOneRate(1);

        Assertions.assertEquals(rate, response.get());
    }

    @Test
    public void getAllRatesTest() {
        Rate rate = new Rate(1,new Locality(), new Locality(),(float)5.00,new Date(),new Date(),(float)2.00, null);
        List<Rate> list = new ArrayList<>();
        list.add(rate);
        Mockito.when(ratesRepository.findAll()).thenReturn(list);
        List<Rate> response = ratesService.getAllRates();

        Assertions.assertEquals(list, response);
    }

    @Test
    public void getRatesByLocalityTest() throws ResourceNotExistException {
        Rate rate = new Rate(1,new Locality(), new Locality(),(float)5.00,new Date(),new Date(),(float)2.00, null);
        Mockito.when(ratesRepository.getRatesByLocality(1,2)).thenReturn(Optional.of(rate));
        Rate response = ratesService.getRatesByLocality(1,2);

        Assertions.assertEquals(rate, response);
    }
/*
    @Test
    public void getRatesByLocalityTest2() throws ResourceNotExistException {
        Mockito.when(ratesService.getRatesByLocality(1,2)).thenReturn(null);
        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            ratesService.getRatesByLocality(1,2);
        });
    }*/
}
