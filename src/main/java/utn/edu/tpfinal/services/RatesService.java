package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.models.Rate;
import utn.edu.tpfinal.repositories.RatesRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RatesService {
    private final RatesRepository ratesRepository;

    @Autowired
    public RatesService(RatesRepository ratesRepository) {
        this.ratesRepository = ratesRepository;
    }

    public Optional<Rate> getOneRate(Integer idRate) {
        return ratesRepository.findById(idRate);
    }

    public List<Rate> getAllRates() {
        return ratesRepository.findAll();
    }

    public Rate getRatesByLocality(Integer idLocalityOrigin, Integer idLocalityDestiny) throws ResourceNotExistException {
        try{
            Optional<Rate> optionalRate = this.ratesRepository.getRatesByLocality(idLocalityOrigin, idLocalityDestiny);
            Rate rate = optionalRate.get();
            return rate;
        }catch (NoSuchElementException e){
            throw new ResourceNotExistException("The rate you want to search does not exist.");
        }
    }
}