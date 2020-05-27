package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.Locality;
import utn.edu.tpfinal.services.LocalityService;

import java.util.List;


@RestController
@RequestMapping("/locality")

public class LocalityController {
    private final LocalityService localityService;

    @Autowired
    public LocalityController(LocalityService localityService) {
        this.localityService = localityService;
    }

    @PostMapping("/")
    public void addLocality(@RequestBody Locality newLocality){
        localityService.addLocality(newLocality);
    }

    @GetMapping("/")
    public List<Locality> getAllLocalities(@RequestParam(required = false) String name){
        return localityService.getAll(name);
    }
}
