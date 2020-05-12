package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.models.Locality;
import utn.edu.tpfinal.repositories.LocalityRepository;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class LocalityService {

    private final LocalityRepository localityRepository;

    @Autowired
    public LocalityService(LocalityRepository localityRepository) {
        this.localityRepository = localityRepository;
    }

    public void addLocality(Locality newLocality) {
        localityRepository.save(newLocality);
    }

    public List<Locality> getAll(String name) {
        if(isNull(name)){
            return localityRepository.findAll();
        }
        return localityRepository.findByLocalityName(name);
    }
}
