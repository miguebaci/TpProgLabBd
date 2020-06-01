package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.models.Province;
import utn.edu.tpfinal.repositories.ProvinceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinceService {

    private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public Optional<Province> getOneProvince(Integer idProvince) {
        return provinceRepository.findById(idProvince);
    }

    public List<Province> getAllProvinces(){
        return provinceRepository.findAll();
    }

    public void addProvince(Province newProvince) {
        provinceRepository.save(newProvince);
    }

    public void deleteOneProvince(Integer idProvince) {
        provinceRepository.deleteById(idProvince);
    }

    public void updateOneProvince(Province newProvince, Integer idProvince) {
        Optional<Province> resultProvince = getOneProvince(idProvince);
        Province currentProvince = resultProvince.get();

        if(resultProvince != null) {
            currentProvince.setIdProv(newProvince.getIdProv());
            currentProvince.setProvinceName(newProvince.getProvinceName());
            currentProvince.setLocalities(newProvince.getLocalities());
            addProvince(currentProvince);
        }
    }

}
