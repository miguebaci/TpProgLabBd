package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.LineType;
import utn.edu.tpfinal.services.LineTypeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lineType")
public class LineTypeController {

    private final LineTypeService lineTypeService;

    @Autowired
    public LineTypeController(LineTypeService lineTypeService) {
        this.lineTypeService = lineTypeService;
    }

    // GET ONE LINE TYPE BY ID.
    @GetMapping("/{idLineType}")
    public Optional<LineType> getLineType(@PathVariable Integer idLineType){
        return lineTypeService.getOneLineType(idLineType);
    }

    // GET ALL LINE TYPES.
    @GetMapping("/")
    public List<LineType> getLineTypes(){
        return lineTypeService.getAllLineTypes();
    }

    // POST LINE TYPE.
    @PostMapping("/")
    public void addLineType(@RequestBody LineType newLineType){
        lineTypeService.addLineType(newLineType);
    }

    // DELETE ONE LINE TYPE BY ID.
    @DeleteMapping("/{idLineType}")
    public void deleteLineType(@PathVariable Integer idLineType){
        lineTypeService.deleteOneLineType(idLineType);
    }

    // UPDATE LINE TYPE BY ID.
    @PutMapping("/{idLineType}")
    public void updateProvince(@RequestBody LineType lineType, @PathVariable Integer idLineType){
        lineTypeService.updateOneLineType(lineType, idLineType);
    }
}
