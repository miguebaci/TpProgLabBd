package utn.edu.tpfinal.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import utn.edu.tpfinal.models.Province;
import utn.edu.tpfinal.services.ProvinceService;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ProvinceControllerTest {

    private ProvinceService provinceService;
    private ProvinceController provinceController;

    @Before
    public void setUp() throws Exception {
        provinceService = mock(ProvinceService.class);
        provinceController = new ProvinceController(provinceService);
    }

    @Test
    public void getProvince() {
        Integer id= 1;
        Province province = new Province(id,"Corrientes",null);

        Optional<Province> expected= Optional.of(province);

        Mockito.when(provinceService.getOneProvince(id)).thenReturn(Optional.of(province));

        Optional<Province> result = provinceController.getProvince(id);

        assertEquals(expected,result);
    }

    @Test
    public void getProvinces() {
    }

    @Test
    public void addProvince() {
    }

    @Test
    public void deleteProvince() {
    }

    @Test
    public void updateProvince() {
    }
}