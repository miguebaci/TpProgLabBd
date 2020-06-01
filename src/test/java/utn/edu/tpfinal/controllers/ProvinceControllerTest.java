package utn.edu.tpfinal.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import utn.edu.tpfinal.models.Province;
import utn.edu.tpfinal.services.ProvinceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)

public class ProvinceControllerTest {
    @InjectMocks
    private ProvinceController provinceController;
    @Mock
    private ProvinceService provinceService;

    @Test
    //public void addProvince(@RequestBody Province newProvince){provinceService.addProvince(newProvince);}
    public void addProvinceTest(){
        Province province = new Province(1,"Buenos Aires",null);
        doNothing().when(provinceService).addProvince(province);
        provinceController.addProvince(province);

        verify(provinceService, Mockito.times(1)).addProvince(province);
    }

    //public List<Province> getProvinces(@RequestParam(required = false) String name){return provinceService.getAll(name);}
    @Test
    public void getProvincesTest(){
        Province province = new Province(1,"Buenos Aires",null);
        Province province2 = new Province(2,"La Pampa",null);
        List<Province> list= new ArrayList<Province>();
        list.add(province);
        list.add(province2);
        Mockito.when(provinceService.getOneProvince(1)).thenReturn(Optional.of(province));
        Optional<Province> response = provinceController.getProvince(1);

        assertNotNull(response);
        assertEquals(province, response.get());
    }

    @Test
    public void getAllProvincesTest(){
        Province province = new Province(1,"Buenos Aires",null);
        Province province2 = new Province(2,"La Pampa",null);
        List<Province> list= new ArrayList<Province>();
        list.add(province);
        list.add(province2);
        Mockito.when(provinceService.getAllProvinces()).thenReturn(list);
        List<Province> response = provinceController.getProvinces();

        assertNotNull(response);
        assertEquals(list, response);
    }
}