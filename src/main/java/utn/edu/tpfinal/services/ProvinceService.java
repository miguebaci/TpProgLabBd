package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.models.Province;
import utn.edu.tpfinal.repositories.ProvinceRepository;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class ProvinceService {

    private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public void addProvince(Province newProvince) {
        provinceRepository.save(newProvince);
    }

    public List<Province> getAll(String name){
        if(isNull(name)){
            return provinceRepository.findAll();
        }
        return provinceRepository.findByProvinceName(name);
    }
}
