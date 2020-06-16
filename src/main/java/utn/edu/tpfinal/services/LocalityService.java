package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.models.Locality;
import utn.edu.tpfinal.repositories.LocalityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LocalityService {

    private final LocalityRepository localityRepository;

    @Autowired
    public LocalityService(LocalityRepository localityRepository) {
        this.localityRepository = localityRepository;
    }

    public Optional<Locality> getOneLocality(Integer prefix) {
        return localityRepository.findById(prefix);
    }

    public List<Locality> getAllLocalities(){
        return localityRepository.findAll();
    }

    public void addLocality(Locality newLocality) {
        localityRepository.save(newLocality);
    }

    public void deleteOneLocality(Integer prefix) {
        localityRepository.deleteById(prefix);
    }

    public void updateOneLocality(Locality newLocality, Integer prefix) {
        Optional<Locality> resultLocality = getOneLocality(prefix);
        Locality currentLocality = resultLocality.get();

        if(resultLocality != null) {
            currentLocality.setPrefix(newLocality.getPrefix());
            currentLocality.setLocalityName(newLocality.getLocalityName());
            currentLocality.setProvince(newLocality.getProvince());
            currentLocality.setPhoneLines(newLocality.getPhoneLines());
            currentLocality.setRates(newLocality.getRates());
            addLocality(currentLocality);
        }
    }
}
