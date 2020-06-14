package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.edu.tpfinal.models.Locality;
import utn.edu.tpfinal.services.LocalityService;

import java.util.List;
import java.util.Optional;


@Controller
public class LocalityController {
    private final LocalityService localityService;

    @Autowired
    public LocalityController(LocalityService localityService) {
        this.localityService = localityService;
    }

    // GET ONE LOCALITY BY PREFIX.
    public Optional<Locality> getLocality(Integer prefix){
        return localityService.getOneLocality(prefix);
    }

    // GET ALL LOCALITIES.
    public List<Locality> getLocalities(){
        return localityService.getAllLocalities();
    }

    // POST LOCALITY.
    public void addLocality(Locality newLocality){
        localityService.addLocality(newLocality);
    }

    // DELETE ONE LOCALITY BY PREFIX.
    public void deleteProvince(Integer prefix){
        localityService.deleteOneLocality(prefix);
    }

    // UPDATE LOCALITY BY PREFIX.
    public void updateProvince(Locality locality, Integer prefix){
        localityService.updateOneLocality(locality, prefix);
    }
}
