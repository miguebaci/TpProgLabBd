package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.edu.tpfinal.models.LineType;
import utn.edu.tpfinal.services.LineTypeService;

import java.util.List;
import java.util.Optional;

@Controller
public class LineTypeController {

    private final LineTypeService lineTypeService;

    @Autowired
    public LineTypeController(LineTypeService lineTypeService) {
        this.lineTypeService = lineTypeService;
    }

    // GET ONE LINE TYPE BY ID.
    public Optional<LineType> getLineType(Integer idLineType){
        return lineTypeService.getOneLineType(idLineType);
    }

    // GET ALL LINE TYPES.
    public List<LineType> getLineTypes(){
        return lineTypeService.getAllLineTypes();
    }

    // POST LINE TYPE.
    public void addLineType(LineType newLineType){
        lineTypeService.addLineType(newLineType);
    }

    // DELETE ONE LINE TYPE BY ID.
    public void deleteLineType(Integer idLineType){
        lineTypeService.deleteOneLineType(idLineType);
    }

    // UPDATE LINE TYPE BY ID.
    public void updateProvince(LineType lineType, Integer idLineType){
        lineTypeService.updateOneLineType(lineType, idLineType);
    }
}
