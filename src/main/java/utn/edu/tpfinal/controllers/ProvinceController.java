package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.Province;
import utn.edu.tpfinal.services.ProvinceService;

import java.util.List;

@RestController
@RequestMapping("/province")
public class ProvinceController {
    private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @PostMapping("/")
    public void addProvince(@RequestBody Province newProvince){
        provinceService.addProvince(newProvince);
    }

    @GetMapping("/")
    public List<Province> getProvinces(@RequestParam(required = false) String name){
        return provinceService.getAll(name);
    }

}
