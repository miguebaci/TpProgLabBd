package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.models.LineType;
import utn.edu.tpfinal.repositories.LineTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LineTypeService {

    private final LineTypeRepository lineTypeRepository;

    @Autowired
    public LineTypeService(LineTypeRepository lineTypeRepository) {
        this.lineTypeRepository = lineTypeRepository;
    }

    public Optional<LineType> getOneLineType(Integer idLineType) {
        return lineTypeRepository.findById(idLineType);
    }

    public List<LineType> getAllLineTypes(){
        return lineTypeRepository.findAll();
    }

    public void addLineType(LineType newLineType) {
        lineTypeRepository.save(newLineType);
    }

    public void deleteOneLineType(Integer idLineType) {
        lineTypeRepository.deleteById(idLineType);
    }

    public void updateOneLineType(LineType newLineType, Integer idLineType) {
        Optional<LineType> resultLineType = getOneLineType(idLineType);
        LineType currentLineType = resultLineType.get();

        if(resultLineType != null) {
            currentLineType.setIdLineType(newLineType.getIdLineType());
            currentLineType.setLineTypeName(newLineType.getLineTypeName());
            currentLineType.setPhoneLines(newLineType.getPhoneLines());
            addLineType(currentLineType);
        }
    }
}
