package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.Locality;
import utn.edu.tpfinal.services.LocalityService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/locality")

public class LocalityController {
    private final LocalityService localityService;

    @Autowired
    public LocalityController(LocalityService localityService) {
        this.localityService = localityService;
    }

    // GET ONE LOCALITY BY PREFIX.
    @GetMapping("/{prefix}")
    public Optional<Locality> getLocality(@PathVariable Integer prefix){
        return localityService.getOneLocality(prefix);
    }

    // GET ALL LOCALITIES.
    @GetMapping("/")
    public List<Locality> getLocalities(){
        return localityService.getAllLocalities();
    }

    // POST LOCALITY.
    @PostMapping("/")
    public void addLocality(@RequestBody Locality newLocality){
        localityService.addLocality(newLocality);
    }

    // DELETE ONE LOCALITY BY PREFIX.
    @DeleteMapping("/{prefix}")
    public void deleteProvince(@PathVariable Integer prefix){
        localityService.deleteOneLocality(prefix);
    }

    // UPDATE LOCALITY BY PREFIX.
    @PutMapping("/{prefix}")
    public void updateProvince(@RequestBody Locality locality, @PathVariable Integer prefix){
        localityService.updateOneLocality(locality, prefix);
    }
}
