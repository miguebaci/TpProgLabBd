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
import utn.edu.tpfinal.dto.CallsForUserDTO;
import utn.edu.tpfinal.models.Bill;
import utn.edu.tpfinal.models.Call;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.models.Rate;
import utn.edu.tpfinal.projections.ITop10DestinationCalled;
import utn.edu.tpfinal.repositories.CallRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class CallsServiceTest {

    @InjectMocks
    private CallsService callsService;
    @Mock
    private CallRepository callsRepository;
    @Mock
    private ITop10DestinationCalled top10;

    @Test
    public void getOneCallTest() {
        Call calls = new Call();
        Mockito.when(callsRepository.findById(1)).thenReturn(Optional.of(calls));
        Optional<Call> response = callsService.getOneCall(1);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(calls, response.get());
    }

    @Test
    public void getAllCallsTest() {
        List<Call> list = new ArrayList<>();
        list.add(new Call());
        when(callsRepository.findAll()).thenReturn(list);
        List<Call> response = callsService.getAllCalls();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(list, response);
    }/*
    @Test
    public void addCallTest() throws ResourceNotExistException {
        CallsForUserDTO c = new CallsForUserDTO(new Date(),10,"2237654321","2231234567");
        Call call = new Call();
        PhoneLine origin = new PhoneLine(1,new User(),new Locality(),LineType.landline,"2237654321",false, null);
        PhoneLine destiny = new PhoneLine(2,new User(),new Locality(),LineType.landline,"2231234567",false, null);
        when(phoneLineService.getByLineNumber(c.getNumberOrigin())).thenReturn(origin);
        when(phoneLineService.getByLineNumber(c.getNumberDestiny())).thenReturn(destiny);
        when(callsRepository.save(call)).thenReturn(call);
        //when(origin.getSuspended()).thenReturn(false);
        //when(destiny.getSuspended()).thenReturn(false);
        Call response = callsService.addCall(c);

        Assertions.assertNotNull(response);
    }*/

    /**
     * public Call addCall(CallsForUserDTO callDto) throws ResourceNotExistException {
     * PhoneLine from = phoneLineService.getByLineNumber(callDto.getNumberOrigin());
     * PhoneLine to = phoneLineService.getByLineNumber(callDto.getNumberDestiny());
     * Call call = new Call();
     * if (!from.getSuspended() && !to.getSuspended()) {
     * if (callDto.getDuration() > 0) {
     * call.setDateCall(callDto.getDateCall());
     * call.setDuration(callDto.getDuration());
     * call.setNumberOrigin(callDto.getNumberOrigin());
     * call.setNumberDestiny(callDto.getNumberDestiny());
     * return callRepository.save(call);
     * }
     * else return call;
     * }
     * else throw new ResourceNotExistException("Verify suspension of the phonelines.");
     * }
     */


    @Test
    public void getCallsBetweenRangeTest() {
        java.sql.Date dateFrom = java.sql.Date.valueOf("2020-06-06");
        java.sql.Date dateTo = Date.valueOf("2020-06-06");
        List<Call> list = new ArrayList<>();
        List<CallsForUserDTO> listUserDtoCalls = new ArrayList<>();
        list.add(new Call(1, new PhoneLine(), new PhoneLine(), new Bill(), new Rate(), (float) 20.00, (float) 5.00, (float) 15.00, dateFrom, dateTo, 10, "22351234567", "22357654321"));
        listUserDtoCalls.add(new CallsForUserDTO(dateFrom, 10, "22351234567", "22357654321"));
        when(callsRepository.getCallsFromUserAsCallerBetweenDates(dateFrom, dateTo, "22351234567")).thenReturn(list);
        List<CallsForUserDTO> response = callsService.getCallsBetweenRange("2020-06-06", "2020-06-06", "22351234567", true);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(listUserDtoCalls, response);
    }

    @Test
    public void getTop10DestinationsWithNumberOfCallsTest() {
        List<ITop10DestinationCalled> list = new ArrayList<>();
        list.add(top10);
        when(callsRepository.getTop10Info(1)).thenReturn(list);
        List<ITop10DestinationCalled> response = callsService.getTop10DestinationsWithNumberOfCalls(1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(list, response);
    }

    /**
     public List<CallsForUserDTO> getCallsBetweenRange(String from, String to, String lineNumber, Boolean caller) {

     // converting a string to a sql date
     java.sql.Date fromDate = java.sql.Date.valueOf(from);
     java.sql.Date toDate = Date.valueOf(to);

     List<Call> userCalls;
     List<CallsForUserDTO> listUserDtoCalls = new ArrayList<>();

     if (caller) {
     userCalls = callRepository.getCallsFromUserAsCallerBetweenDates(fromDate, toDate, lineNumber);
     } else {
     userCalls = callRepository.getCallsFromUserAsReceiverBetweenDates(fromDate, toDate, lineNumber);
     }

     // we pass the information to the calls dto
     for (Call c : userCalls) {
     Float price = null;

     if (caller) {
     price = c.getPrice();
     }

     listUserDtoCalls.add(new CallsForUserDTO(c.getDateCall(),
     c.getDuration(), c.getNumberOrigin(), c.getNumberDestiny()));
     }

     return listUserDtoCalls;
     }

     public List<CallsForUserDTO> getCallsForUserDTO(String lineNumber, Boolean caller) {
     List<Call> userCalls;
     List<CallsForUserDTO> listUserDtoCalls = new ArrayList<>();

     if (caller) {
     userCalls = callRepository.getCallsFromUserAsCaller(lineNumber);
     } else {
     userCalls = callRepository.getCallsFromUserAsReceiver(lineNumber);
     }

     // we pass the information to the calls dto
     for (Call c : userCalls) {
     Float price = null;

     if (caller) {
     price = c.getPrice();
     }

     listUserDtoCalls.add(new CallsForUserDTO(c.getDateCall(),
     c.getDuration(), c.getNumberOrigin(), c.getNumberDestiny()));
     }

     return listUserDtoCalls;
     }
     */
}
