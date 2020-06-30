package utn.edu.tpfinal.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class PhoneLineServiceTest {



    /**
     public PhoneLineService(PhoneLineRepository phoneLineRepository, RatesRepository ratesRepository) {
     this.phoneLineRepository = phoneLineRepository;
     this.ratesRepository = ratesRepository;
     }

     public Optional<PhoneLine> getOnePhoneLine(Integer idPhoneLine) {
     return phoneLineRepository.findById(idPhoneLine);
     }

     public PhoneLine getByLineNumber(String lineNumber) {
     return phoneLineRepository.findByLineNumber(lineNumber).get();
     }

     public void addPhoneLine(PhoneLine phoneLine) {
     phoneLineRepository.save(phoneLine);
     }

     public void deleteOnePhoneLine(Integer idPhoneLine) {
     phoneLineRepository.deleteById(idPhoneLine);
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

     */
}
