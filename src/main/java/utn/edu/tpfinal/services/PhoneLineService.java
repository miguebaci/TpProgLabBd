package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.repositories.PhoneLineRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneLineService {
    private final PhoneLineRepository phoneLineRepository;

    @Autowired
    public PhoneLineService(PhoneLineRepository phoneLineRepository) {
        this.phoneLineRepository = phoneLineRepository;
    }

    public Optional<PhoneLine> getOnePhoneLine(Integer idPhoneLine) {
        return phoneLineRepository.findById(idPhoneLine);
    }

    public PhoneLine getByLineNumber(String lineNumber) {
        return phoneLineRepository.findByLineNumber(lineNumber);
    }

    public List<PhoneLine> getAllPhoneLines() {
        return phoneLineRepository.findAll();
    }

    public void addPhoneLine(PhoneLine phoneLine) {
        phoneLineRepository.save(phoneLine);
    }

    public void deleteOnePhoneLine(Integer idPhoneLine) {
        phoneLineRepository.deleteById(idPhoneLine);
    }

    public void updateOnePhoneLine(PhoneLine newPhoneLine, Integer idPhoneLine) {
        Optional<PhoneLine> resultPhoneLine = getOnePhoneLine(idPhoneLine);
        PhoneLine currentPhoneLine = resultPhoneLine.get();

        if (resultPhoneLine != null) {
            currentPhoneLine.setIdLine(newPhoneLine.getIdLine());
            currentPhoneLine.setUser(newPhoneLine.getUser());
            currentPhoneLine.setLocality(newPhoneLine.getLocality());
            currentPhoneLine.setLineType(newPhoneLine.getLineType());
            currentPhoneLine.setLineNumber(newPhoneLine.getLineNumber());
            currentPhoneLine.setCalls(newPhoneLine.getCalls());
            addPhoneLine(currentPhoneLine);
        }
    }

    public void activePhoneLine(Integer idPhoneLine) {
        Optional<PhoneLine> resultPhoneLine = getOnePhoneLine(idPhoneLine);
        PhoneLine currentPhoneLine = resultPhoneLine.get();

        if (resultPhoneLine != null) {
            if (resultPhoneLine != null) {
                if (currentPhoneLine.getSuspended()) {
                    currentPhoneLine.setSuspended(false);
                } else currentPhoneLine.setSuspended(true);
                addPhoneLine(currentPhoneLine);
            }
        }
    }
}
