package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.models.Locality;
import utn.edu.tpfinal.models.Province;
import utn.edu.tpfinal.repositories.LocalityRepository;
import utn.edu.tpfinal.repositories.ProvinceRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class LocalityService {

    private final LocalityRepository localityRepository;
    private final ProvinceRepository provinceRepository;

    @Autowired
    public LocalityService(LocalityRepository localityRepository, ProvinceRepository provinceRepository) {
        this.localityRepository = localityRepository;
        this.provinceRepository = provinceRepository;
    }

    public void addLocality(Locality newLocality) {
        // We save the locality
        localityRepository.save(newLocality);
    }

    public List<Locality> getAll(String name) {
        if(isNull(name)){
            return localityRepository.findAll();
        }

        return localityRepository.findByLocalityName(name);

        /*List<Locality> myLocalities = localityRepository.findAll();


        for (Locality loc: myLocalities) {
            Province province = provinceRepository.findByProvinceId(loc.getProvinceId());
            province.setLocalities(null);
            loc.setFrom(province);
        }

        if(isNull(name)){
            return myLocalities;//localityRepository.findAll();
        }

        return localityRepository.findByLocalityName(name);*/
    }
}
