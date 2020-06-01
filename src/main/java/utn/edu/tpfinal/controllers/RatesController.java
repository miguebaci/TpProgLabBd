package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.Rate;
import utn.edu.tpfinal.services.RateService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rates")
public class RatesController {
    private final RateService rateService;

    @Autowired
    public RatesController(RateService rateService) {
        this.rateService = rateService;
    }

    // GET ONE RATE BY ID.
    @GetMapping("/{idRate}")
    public Optional<Rate> getUser(@PathVariable Integer idRate){
        return rateService.getOneRate(idRate);
    }

    // GET ALL RATES.
    @GetMapping("/")
    public List<Rate> getRates(){
        return rateService.getAllRates();
    }

    // POST RATE.
    @PostMapping("/")
    public void addRate(@RequestBody Rate newRate){
        rateService.addRate(newRate);
    }

    // DELETE ONE RATE BY ID.
    @DeleteMapping("/{idRate}")
    public void deleteRate(@PathVariable Integer idRate){
        rateService.deleteOneRate(idRate);
    }

    // UPDATE RATE.
    @PutMapping("/{idRate}")
    public void updateRate(@RequestBody Rate rate, @PathVariable Integer idRate){
        rateService.updateOneRate(rate, idRate);
    }
}
