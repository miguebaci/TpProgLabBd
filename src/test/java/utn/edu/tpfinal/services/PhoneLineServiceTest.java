package utn.edu.tpfinal.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.dto.BillForUserDTO;
import utn.edu.tpfinal.dto.PhoneLineForUserDTO;
import utn.edu.tpfinal.models.*;
import utn.edu.tpfinal.repositories.PhoneLineRepository;
import utn.edu.tpfinal.repositories.RatesRepository;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class PhoneLineServiceTest {
    @InjectMocks
    private PhoneLineService phoneLineService;
    @Mock
    private PhoneLineRepository phoneLineRepository;



    @Test
    public void getOnePhoneLineTest() {
        PhoneLine line = new PhoneLine();
        Mockito.when(phoneLineRepository.findById(1)).thenReturn(Optional.of(line));
        Optional<PhoneLine> response = phoneLineService.getOnePhoneLine(1);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(line, response.get());
    }

    @Test
    public void getByLineNumberTest() {
        PhoneLine line = new PhoneLine();
        Mockito.when(phoneLineRepository.findByLineNumber("2231234567")).thenReturn(Optional.of(line));
        PhoneLine response = phoneLineService.getByLineNumber("2231234567");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(line, response);
    }


    @Test
    public void addPhoneLineTest() {
        PhoneLine line = new PhoneLine();
        Mockito.when(phoneLineRepository.save(line)).thenReturn(line);
        phoneLineService.addPhoneLine(line);

        Mockito.verify(phoneLineRepository, times(1)).save(line);
    }

    @Test
    public void deleteOnePhoneLineTest() {
        Integer id = 1;
        doNothing().when(phoneLineRepository).deleteById(id);
        phoneLineService.deleteOnePhoneLine(id);

        Mockito.verify(phoneLineRepository, times(1)).deleteById(1);
    }


    @Test
    public void getPhoneLinesForUserDTOTest(){
        List<PhoneLine> list = new ArrayList<>();
        List<PhoneLineForUserDTO> list2 = new ArrayList<>();
        list.add(new PhoneLine(1,new User(),new Locality(),LineType.landline,"2231234567",false,null));
        list2.add(new PhoneLineForUserDTO("2231234567","landline"));
        when(phoneLineRepository.getUserPhoneLines(1)).thenReturn(list);
        List<PhoneLineForUserDTO> response = phoneLineService.getPhoneLinesForUserDTO(1);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(list2, response);
    }
    /**

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
     */

    @Test
    public void getOnePhoneLineByUserTest() {
        PhoneLine line = new PhoneLine();
        Mockito.when(phoneLineRepository.getPhoneLineByUserId("2231234567",1)).thenReturn(line);
        PhoneLine response = phoneLineService.getOnePhoneLineByUser("2231234567", 1);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(line, response);
    }

    @Test
    public void activePhoneLineTest() throws ResourceNotExistException {
        PhoneLine line = new PhoneLine(1,new User(),new Locality(),LineType.landline,"223546879",false,null);
        PhoneLine updated = new PhoneLine(1,new User(),new Locality(),LineType.landline,"223546879",true,null);
        Mockito.when(phoneLineService.getOnePhoneLine(1)).thenReturn(Optional.of(line));
        when(phoneLineRepository.save(line)).thenReturn(updated);
        phoneLineService.activePhoneLine(1);

        Assertions.assertNotNull(updated);
        Assertions.assertEquals(line, updated);

        when(phoneLineRepository.findById(1)).thenThrow(NoSuchElementException.class);
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            phoneLineService.getOnePhoneLine(1);
        });
    }

    @Test
    public void activePhoneLineTest2() throws ResourceNotExistException {
        PhoneLine line = new PhoneLine(1,new User(),new Locality(),LineType.landline,"223546879",true,null);
        PhoneLine updated = new PhoneLine(1,new User(),new Locality(),LineType.landline,"223546879",false,null);
        Mockito.when(phoneLineService.getOnePhoneLine(1)).thenReturn(Optional.of(line));
        when(phoneLineRepository.save(line)).thenReturn(updated);
        phoneLineService.activePhoneLine(1);

        Assertions.assertNotNull(updated);
        Assertions.assertEquals(line, updated);

        when(phoneLineRepository.findById(1)).thenThrow(NoSuchElementException.class);
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            phoneLineService.getOnePhoneLine(1);
        });
    }
}
