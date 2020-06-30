package utn.edu.tpfinal.controllers;

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
import utn.edu.tpfinal.Exceptions.ValidationException;
import utn.edu.tpfinal.dto.CallsForUserDTO;
import utn.edu.tpfinal.models.*;
import utn.edu.tpfinal.projections.ITop10DestinationCalled;
import utn.edu.tpfinal.services.CallsService;
import utn.edu.tpfinal.services.PhoneLineService;
import utn.edu.tpfinal.services.UserService;
import utn.edu.tpfinal.session.SessionManager;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class CallsControllerTest {
    @InjectMocks
    private CallsController callsController;

    @Mock
    private CallsService callsService;

    @Mock
    private PhoneLineService phoneLineService;

    @Mock
    private ITop10DestinationCalled iTop10DestinationCalled;

    @Test
    public void getCallTest() {
       Call c = new Call(1, new PhoneLine(), new PhoneLine(), new Bill(), new Rate(), (float)100, (float)20, (float)80, Date.valueOf("2020-01-10"), Date.valueOf("2020-01-10"), 1, "223155101234", "223155419080");
       Mockito.when(callsService.getOneCall(c.getIdCall())).thenReturn(Optional.ofNullable(c));
       Optional<Call> response =  callsController.getCall(c.getIdCall());
       Assertions.assertNotNull(response);
       Assertions.assertEquals(c,response.get());
    }

    @Test
    public void getCallsTest() {
        List<Call> myCalls = new ArrayList<>();
        Call a = new Call(1, new PhoneLine(), new PhoneLine(), new Bill(), new Rate(), (float)100, (float)20, (float)80, Date.valueOf("2020-01-10"), Date.valueOf("2020-01-10"), 1, "223155101234", "223155419080");
        Call b = new Call(2, new PhoneLine(), new PhoneLine(), new Bill(), new Rate(), (float)100, (float)20, (float)80, Date.valueOf("2020-02-10"), Date.valueOf("2020-02-10"), 1, "223155101234", "223155419080");
        myCalls.add(a);
        myCalls.add(b);

        Mockito.when(callsService.getAllCalls()).thenReturn(myCalls);
        List<Call> response =  callsController.getCalls();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(myCalls,response);
    }

    @Test
    public void addCallTest() throws ResourceNotExistException {
        CallsForUserDTO callDto = new CallsForUserDTO(Date.valueOf("2020-02-10"), 1, "223155211010", "223155161090");
        Call call= new Call();
        call.setDateCall(callDto.getDateCall());
        call.setDuration(callDto.getDuration());
        call.setNumberOrigin(callDto.getNumberOrigin());
        call.setNumberDestiny(callDto.getNumberDestiny());

        Mockito.when(callsService.addCall(callDto)).thenReturn(call);
        Call response = callsController.addCall(callDto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(call,response);
    }


    @Test
    public void getCallsByUserTest() throws ResourceNotExistException {
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        PhoneLine pl = new PhoneLine(1, u, null, LineType.mobile, "223155123456", false, null);
        List<CallsForUserDTO> myCallsDTO = new ArrayList<>();
        CallsForUserDTO callDto = new CallsForUserDTO(Date.valueOf("2020-02-10"), 1, "223155211010", "223155161090");
        CallsForUserDTO callDtoTwo = new CallsForUserDTO(Date.valueOf("2020-03-10"), 1, "223155211010", "223155161090");
        myCallsDTO.add(callDto);
        myCallsDTO.add(callDtoTwo);

        Mockito.when(phoneLineService.getOnePhoneLineByUser(pl.getLineNumber(), u.getId())).thenReturn(pl);
        Mockito.when(callsService.getCallsForUserDTO(pl.getLineNumber(), true)).thenReturn(myCallsDTO);
        List<CallsForUserDTO> response = callsController.getCallsByUser(u.getId(), pl.getLineNumber());

        Assertions.assertEquals(myCallsDTO,response);

        // Check exception
        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            callsController.getCallsByUser(null, pl.getLineNumber());
        });
    }

    @Test
    public void getCallsBetweenRangeOfDatesTest() throws ValidationException {
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        PhoneLine pl = new PhoneLine(1, u, null, LineType.mobile, "223155123456", false, null);
        List<CallsForUserDTO> myCallsDTO = new ArrayList<>();
        List<CallsForUserDTO> allCallsDTO = new ArrayList<>();
        CallsForUserDTO callDto = new CallsForUserDTO(Date.valueOf("2020-02-10"), 1, "223155211010", "223155161090");
        CallsForUserDTO callDtoTwo = new CallsForUserDTO(Date.valueOf("2020-03-10"), 1, "223155211010", "223155161090");
        CallsForUserDTO callDtoThree = new CallsForUserDTO(Date.valueOf("2020-06-10"), 1, "223155211010", "223155161090");
        myCallsDTO.add(callDto);
        myCallsDTO.add(callDtoTwo);

        // Check the first exception
        Assertions.assertThrows(ValidationException.class, () -> {
            callsController.getCallsBetweenRangeOfDates(u, "2020-02-10", "2020-03-10", null, true);
        });

        // Check if we have valid dates
        Mockito.when(phoneLineService.getOnePhoneLineByUser(pl.getLineNumber(), u.getId())).thenReturn(pl);
        Mockito.when(callsService.getCallsBetweenRange("2020-02-10", "2020-03-10", "223155123456", true)).thenReturn(myCallsDTO);
        List<CallsForUserDTO> response = callsController.getCallsBetweenRangeOfDates(u, "2020-02-10", "2020-03-10", "223155123456", true);

        Assertions.assertEquals(myCallsDTO,response);

        // Check if we have a null date
        allCallsDTO.add(callDto);
        allCallsDTO.add(callDtoTwo);
        allCallsDTO.add(callDtoThree);
        Mockito.when(phoneLineService.getOnePhoneLineByUser(pl.getLineNumber(), u.getId())).thenReturn(pl);
        Mockito.when(callsService.getCallsForUserDTO("223155123456", true)).thenReturn(allCallsDTO);
        List<CallsForUserDTO> responseTwo = callsController.getCallsBetweenRangeOfDates(u, null, null , "223155123456", true);

        Assertions.assertEquals(allCallsDTO,responseTwo);
    }


    @Test
    public void getTop10DestinationWithNumberOfCallsTEst(){
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        List<ITop10DestinationCalled> list = new ArrayList<>();
        list.add(iTop10DestinationCalled);
        Mockito.when(callsService.getTop10DestinationsWithNumberOfCalls(u.getId())).thenReturn(list);

        List<ITop10DestinationCalled> response = callsController.getTop10DestinationWithNumberOfCalls(u);
        Assertions.assertEquals(list,response);
    }
}
