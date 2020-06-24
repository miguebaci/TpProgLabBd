package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.models.Rate;
import utn.edu.tpfinal.repositories.RateRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RateService {
    private final RateRepository rateRepository;

    @Autowired
    public RateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public Optional<Rate> getOneRate(Integer idRate) {
        return rateRepository.findById(idRate);
    }

    public List<Rate> getAllRates() {
        return rateRepository.findAll();
    }

    public Rate getRatesByLocality(Integer idLocalityOrigin, Integer idLocalityDestiny) throws ResourceNotExistException {
        return this.rateRepository.getRatesByLocality(idLocalityOrigin, idLocalityDestiny).orElseThrow(ResourceNotExistException::new);
    }
}