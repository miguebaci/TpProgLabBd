package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public List<Rate> getAllRates(){
        return rateRepository.findAll();
    }

    public void addRate(Rate newRate) {
        rateRepository.save(newRate);
    }

    public void deleteOneRate(Integer idRate) {
        rateRepository.deleteById(idRate);
    }

    public void updateOneRate(Rate newRate, Integer idRate) {
        Optional<Rate> resultRate = getOneRate(idRate);
        Rate currentRate = resultRate.get();

        if(resultRate != null) {
            currentRate.setIdRate(newRate.getIdRate());
            currentRate.setLocalityOrigin(newRate.getLocalityOrigin());
            currentRate.setLocalityDestiny(newRate.getLocalityDestiny());
            currentRate.setPricePerMinute(newRate.getPricePerMinute());
            currentRate.setStartDate(newRate.getStartDate());
            currentRate.setExpirationDate(newRate.getExpirationDate());
            currentRate.setCost(newRate.getCost());
            currentRate.setCalls(newRate.getCalls());
            addRate(currentRate);
        }
    }
}
