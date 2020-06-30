package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.models.Rate;
import utn.edu.tpfinal.services.RatesService;

@Controller
public class RatesController {
    private final RatesService ratesService;

    @Autowired
    public RatesController(RatesService ratesService) {
        this.ratesService = ratesService;
    }

    public Rate getRatesByLocality(Integer idLocalityOrigin, Integer idLocalityDestiny) throws ResourceNotExistException {
        return this.ratesService.getRatesByLocality(idLocalityOrigin, idLocalityDestiny);
    }
}
