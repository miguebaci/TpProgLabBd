package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.Rate;
import utn.edu.tpfinal.services.RateService;

import java.util.List;

@RestController
@RequestMapping("/rates")
public class RatesController {
    private final RateService rateService;

    @Autowired
    public RatesController(RateService rateService) {
        this.rateService = rateService;
    }

    @PostMapping("/")
    public void addRate(@RequestBody Rate rate){
        rateService.addRate(rate);
    }

    @GetMapping("/")
    public List<Rate> getAllRates(@RequestParam(required = false) Integer idRate){
        return rateService.getAll(idRate);
    }
}
