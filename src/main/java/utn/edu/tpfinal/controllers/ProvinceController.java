package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.Province;
import utn.edu.tpfinal.services.ProvinceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/province")
public class ProvinceController {
    private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    // GET ONE PROVINCE BY ID.
    @GetMapping("/{idProvince}")
    public Optional<Province> getProvince(@PathVariable Integer idProvince){
        return provinceService.getOneProvince(idProvince);
    }

    // GET ALL PROVINCES.
    @GetMapping("/")
    public List<Province> getProvinces(){
        return provinceService.getAllProvinces();
    }

    // POST PROVINCE.
    @PostMapping("/")
    public void addProvince(@RequestBody Province newProvince){
        provinceService.addProvince(newProvince);
    }

    // DELETE ONE PROVINCE BY ID.
    @DeleteMapping("/{idProvince}")
    public void deleteProvince(@PathVariable Integer idProvince){
        provinceService.deleteOneProvince(idProvince);
    }

    // UPDATE PROVINCE.
    @PutMapping("/{idProvince}")
    public void updateProvince(@RequestBody Province province, @PathVariable Integer idProvince){
        provinceService.updateOneProvince(province, idProvince);
    }
}
