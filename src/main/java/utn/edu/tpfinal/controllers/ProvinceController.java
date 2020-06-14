package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.edu.tpfinal.models.Province;
import utn.edu.tpfinal.services.ProvinceService;

import java.util.List;
import java.util.Optional;

@Controller
public class ProvinceController {
    private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    // GET ONE PROVINCE BY ID.
    public Optional<Province> getProvince(Integer idProvince){
        return provinceService.getOneProvince(idProvince);
    }

    // GET ALL PROVINCES.
    public List<Province> getProvinces(){
        return provinceService.getAllProvinces();
    }

    // POST PROVINCE.
    public void addProvince(Province newProvince){
        provinceService.addProvince(newProvince);
    }

    // DELETE ONE PROVINCE BY ID.
    public void deleteProvince(Integer idProvince){
        provinceService.deleteOneProvince(idProvince);
    }

    // UPDATE PROVINCE.
    public void updateProvince(Province province, Integer idProvince){
        provinceService.updateOneProvince(province, idProvince);
    }
}
