package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.dto.PhoneLineForUserDTO;
import utn.edu.tpfinal.dto.Top10DestinationCalledDTO;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.projections.ITop10DestinationCalled;
import utn.edu.tpfinal.repositories.PhoneLineRepository;
import utn.edu.tpfinal.repositories.RateRepository;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PhoneLineService {
    private final PhoneLineRepository phoneLineRepository;
    private final RateRepository rateRepository;

    @Autowired
    public PhoneLineService(PhoneLineRepository phoneLineRepository, RateRepository rateRepository) {
        this.phoneLineRepository = phoneLineRepository;
        this.rateRepository = rateRepository;
    }

    public Optional<PhoneLine> getOnePhoneLine(Integer idPhoneLine) {
        return phoneLineRepository.findById(idPhoneLine);
    }

    public PhoneLine getByLineNumber(String lineNumber) {
        return phoneLineRepository.findByLineNumber(lineNumber).get();
    }

    public List<PhoneLine> getAllPhoneLines() {
        return phoneLineRepository.findAll();
    }

    public void addPhoneLine(PhoneLine phoneLine) {
        phoneLineRepository.save(phoneLine);
    }

    public ResponseEntity<PhoneLine> addUser(PhoneLine newPhoneLine) throws NoSuchAlgorithmException, ResourceNotExistException {
        Boolean exists = phoneLineRepository.findByLineNumber(newPhoneLine.getLineNumber()).isPresent();
        if (!exists) {
            PhoneLine created = phoneLineRepository.save(PhoneLine.builder()
                    .user(newPhoneLine.getUser())
                    .locality(newPhoneLine.getLocality())
                    .lineType(newPhoneLine.getLineType())
                    .lineNumber(newPhoneLine.getLineNumber())
                    .suspended(false)
                    .build());
            return ResponseEntity.ok(created);
        } else throw new ResourceNotExistException();
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
            phoneLineRepository.save(currentPhoneLine);
        }
    }

    public List<PhoneLineForUserDTO> getPhoneLinesForUserDTO(Integer idUser) {
        // We get the user phone lines searching with its id.
        // Then we wrap them in a dto.

        List<PhoneLine> userPhoneLines = phoneLineRepository.getUserPhoneLines(idUser);
        List<PhoneLineForUserDTO> userDtoPhoneLines = new ArrayList<>();

        // we pass the info to the bill dto
        for (PhoneLine p : userPhoneLines) {
            userDtoPhoneLines.add(new PhoneLineForUserDTO(p.getLineNumber(), p.getLineTypeString()));
        }

        return userDtoPhoneLines;
    }

    public PhoneLine getOnePhoneLineByUser(String lineNumber, Integer idUser) {
        return phoneLineRepository.getPhoneLineByUserId(lineNumber, idUser);
    }

    public void activePhoneLine(Integer idPhoneLine) throws ResourceNotExistException {
        try {
            Optional<PhoneLine> resultPhoneLine = getOnePhoneLine(idPhoneLine);
            PhoneLine currentPhoneLine = resultPhoneLine.get();

            if (resultPhoneLine != null) {
                if (resultPhoneLine != null) {
                    if (currentPhoneLine.getSuspended()) {
                        currentPhoneLine.setSuspended(false);
                    } else currentPhoneLine.setSuspended(true);
                    phoneLineRepository.save(currentPhoneLine);
                }
            }
        }catch (NoSuchElementException e){
            throw new ResourceNotExistException("The phone line you want to activate does not exist");
        }
    }
}
