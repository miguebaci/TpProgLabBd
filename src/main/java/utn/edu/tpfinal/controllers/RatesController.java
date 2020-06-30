package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.models.Rate;
import utn.edu.tpfinal.services.RateService;

@Controller
public class RatesController {
    private final RateService rateService;

    @Autowired
    public RatesController(RateService rateService) {
        this.rateService = rateService;
    }

    public Rate getRatesByLocality(Integer idLocalityOrigin, Integer idLocalityDestiny) throws ResourceNotExistException {
        return this.rateService.getRatesByLocality(idLocalityOrigin, idLocalityDestiny);
    }
}
