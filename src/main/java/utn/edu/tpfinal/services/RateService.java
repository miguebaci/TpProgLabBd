package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.models.Rate;
import utn.edu.tpfinal.repositories.RateRepository;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class RateService {
    private final RateRepository rateRepository;

    @Autowired
    public RateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public void addRate(Rate rate) {
        rateRepository.save(rate);
    }

    public List<Rate> getAll(Integer idRate) {
        if(isNull(idRate)){
            return rateRepository.findAll();
        }
        return rateRepository.findOneByRateId(idRate);
    }
}
