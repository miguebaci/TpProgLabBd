package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.models.LineType;
import utn.edu.tpfinal.repositories.LineTypeRepository;

import java.util.List;

@Service
public class LineTypeService {

    private final LineTypeRepository lineTypeRepository;

    @Autowired
    public LineTypeService(LineTypeRepository lineTypeRepository) {
        this.lineTypeRepository = lineTypeRepository;
    }

    public void addLineType(LineType newLineType) {
        lineTypeRepository.save(newLineType);
    }

    public List<LineType> getAllLineTypes() {
        return lineTypeRepository.findAll();
    }
}
