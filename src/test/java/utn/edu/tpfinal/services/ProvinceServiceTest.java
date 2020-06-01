package utn.edu.tpfinal.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import utn.edu.tpfinal.models.Province;
import utn.edu.tpfinal.repositories.ProvinceRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)

public class ProvinceServiceTest {
    @InjectMocks
    private ProvinceService provinceService;

    @Mock
    private ProvinceRepository provinceRepository;

    @Test
    //public void addProvince(Province newProvince) {provinceRepository.save(newProvince);}
    public void addProvinceTest() {
        Province province = new Province(1,"Buenos Aires",null);
        when(provinceRepository.save(province)).thenReturn(province);
        provinceService.addProvince(province);

        verify(provinceRepository,times(1)).save(province);
    }

    //public List<Province> getAll(String name){
    //        if(isNull(name)){
    //            return provinceRepository.findAll();
    //        }
    //        return provinceRepository.findByProvinceName(name);
    //    }
    @Test
    public void getProvincesTest() {
        Province province = new Province(1,"Buenos Aires",null);
        Province province2 = new Province(2,"La Pampa",null);
        List<Province> list= new ArrayList<Province>();
        list.add(province);
        list.add(province2);
        Mockito.when(provinceRepository.findAll()).thenReturn(list);

        List<Province> response = provinceService.getAllProvinces();
        assertNotNull(response);
        assertEquals(list,response);
    }

    @Test
    public void getAllProvincesTest() {
        Province province = new Province(1,"Buenos Aires",null);
        Province province2 = new Province(2,"La Pampa",null);
        List<Province> list= new ArrayList<Province>();
        list.add(province);
        list.add(province2);
        when(provinceRepository.findAll()).thenReturn(list);

        List<Province> response = provinceService.getAllProvinces();
        assertNotNull(response);
        assertEquals(list,response);
    }
}
