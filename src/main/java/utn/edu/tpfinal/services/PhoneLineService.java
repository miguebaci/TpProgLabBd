package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.repositories.PhoneLineRepository;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class PhoneLineService {
    private final PhoneLineRepository phoneLineRepository;

    @Autowired
    public PhoneLineService(PhoneLineRepository phoneLineRepository) {
        this.phoneLineRepository = phoneLineRepository;
    }

    public void addPhoneLine(PhoneLine phoneLine) {
        phoneLineRepository.save(phoneLine);
    }

    public List<PhoneLine> getAll(Integer idLine) {
        if(isNull(idLine)){
            return phoneLineRepository.findAll();
        }
        return phoneLineRepository.findOneByPhoneLineId(idLine);
    }

}
