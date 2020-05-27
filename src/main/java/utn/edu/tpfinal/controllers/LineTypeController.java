package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.LineType;
import utn.edu.tpfinal.models.Locality;
import utn.edu.tpfinal.services.LineTypeService;

import java.util.List;

@RestController
@RequestMapping("/lineType")
public class LineTypeController {

    private final LineTypeService lineTypeService;

    @Autowired
    public LineTypeController(LineTypeService lineTypeService) {
        this.lineTypeService = lineTypeService;
    }

    @PostMapping("/")
    public void addLineType(@RequestBody LineType newLineType){
        lineTypeService.addLineType(newLineType);
    }

    @GetMapping("/")
    public List<LineType> getLineTypes(){
        return lineTypeService.getAllLineTypes();
    }
}
