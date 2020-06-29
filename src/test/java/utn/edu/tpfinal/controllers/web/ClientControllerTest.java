package utn.edu.tpfinal.controllers.web;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.Exceptions.ValidationException;
import utn.edu.tpfinal.controllers.BillController;
import utn.edu.tpfinal.controllers.CallsController;
import utn.edu.tpfinal.controllers.PhoneLineController;
import utn.edu.tpfinal.controllers.UserController;
import utn.edu.tpfinal.dto.BillForUserDTO;
import utn.edu.tpfinal.dto.CallsForUserDTO;
import utn.edu.tpfinal.dto.UserResponseDTO;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.models.UserType;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.projections.ITop10DestinationCalled;
import utn.edu.tpfinal.session.SessionManager;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class ClientControllerTest {
    @InjectMocks
    private ClientController clientController;

    @Mock
    private UserController userController;
    @Mock
    private BillController billController;
    @Mock
    private CallsController callsController;
    @Mock
    private PhoneLineController phoneLineController;
    @Mock
    private SessionManager sessionManager;
    @Mock
    private IReduceUser iReduceUser;
    @Mock
    private ITop10DestinationCalled iTop10DestinationCalled;

    @Test
    public void getReduceUserTest() throws ResourceNotExistException {
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        String token = "asfsgegegeg";

        Mockito.when(sessionManager.getCurrentUser(token)).thenReturn(u);
        Mockito.when(userController.getReduceUser(u.getId())).thenReturn(iReduceUser);

        ResponseEntity<IReduceUser> response = clientController.getReduceUser(token);

        Assertions.assertEquals(iReduceUser,response.getBody());
        Assertions.assertEquals(200,response.getStatusCodeValue());

        Mockito.when(userController.getReduceUser(u.getId())).thenReturn(null);
        ResponseEntity<IReduceUser> responseTwo = clientController.getReduceUser(token);
        Assertions.assertEquals(404,responseTwo.getStatusCodeValue());

        doThrow(ResourceNotExistException.class)
                .when(sessionManager)
                .getCurrentUser("abcde");

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            clientController.getReduceUser("abcde");
        });
    }

    @Test
    public void getOneUserDTOTest () throws ResourceNotExistException {
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        String token = "asfsgegegeg";

        UserResponseDTO userResponseDTO = new UserResponseDTO(37867266, "dema", "felipe","demaria", null,null);

        Mockito.when(sessionManager.getCurrentUser(token)).thenReturn(u);
        Mockito.when(userController.getOneUserDTO(u.getId())).thenReturn(userResponseDTO);
        ResponseEntity<UserResponseDTO> response = clientController.getOneUserDTO(token);

        Assertions.assertEquals(userResponseDTO,response.getBody());
        Assertions.assertEquals(200,response.getStatusCodeValue());

        // si el user dto es null
        Mockito.when(userController.getOneUserDTO(u.getId())).thenReturn(null);
        ResponseEntity<UserResponseDTO> responseTwo = clientController.getOneUserDTO(token);
        Assertions.assertEquals(404,responseTwo.getStatusCodeValue());

        // si falla la session manager
        doThrow(ResourceNotExistException.class)
                .when(sessionManager)
                .getCurrentUser("abcde");

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            clientController.getOneUserDTO("abcde");
        });
    }

    @Test
    public void getBillsInfoForUser() throws ResourceNotExistException {
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        String token = "123456";

        List<BillForUserDTO> listBillsDTO = new ArrayList<BillForUserDTO>();
        BillForUserDTO bill = new BillForUserDTO( (float) 1500.00, Date.valueOf("2020-10-22"), Date.valueOf("2020-11-22"), false);
        BillForUserDTO billTwo = new BillForUserDTO( (float) 1550.00, Date.valueOf("2020-10-22"), Date.valueOf("2020-11-22"), false);
        listBillsDTO.add(bill);
        listBillsDTO.add(billTwo);

        Mockito.when(sessionManager.getCurrentUser(token)).thenReturn(u);
        Mockito.when(billController.getBillsBetweenRangeOfDates(u,"2020-11-22","2020-11-22")).thenReturn(listBillsDTO);

        ResponseEntity<List<BillForUserDTO>> response = clientController.getBillsInfoForUser(token, "2020-11-22","2020-11-22");

        Assertions.assertEquals(listBillsDTO,response.getBody());
        Assertions.assertEquals(200,response.getStatusCodeValue());

        // Checking when there is no bills
        Mockito.when(billController.getBillsBetweenRangeOfDates(u,"2020-01-22","2020-02-22")).thenReturn(new ArrayList<>());

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            clientController.getBillsInfoForUser(token, "2020-02-22","2020-02-22");
        });
    }

    @Test
    public void getCallsInfoForUser() throws ResourceNotExistException, ValidationException {
        // Test calls between dates
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        String token = "123456";

        List<CallsForUserDTO> listCallsDTO = new ArrayList<CallsForUserDTO>();
        CallsForUserDTO call    = new CallsForUserDTO(Date.valueOf("2020-10-22"),4,"223155111111","223155123456");
        CallsForUserDTO callTwo = new CallsForUserDTO(Date.valueOf("2020-11-22"),4,"223155111111","223155123456");
        listCallsDTO.add(call);
        listCallsDTO.add(callTwo);

        Mockito.when(sessionManager.getCurrentUser(token)).thenReturn(u);
        Mockito.when(callsController.getCallsBetweenRangeOfDates(u,"2020-10-22","2020-12-22","223155111111",true)).thenReturn(listCallsDTO);

        ResponseEntity<List<CallsForUserDTO>> response = clientController.getCallsInfoForUser(token, "2020-10-22","2020-12-22","223155111111",true);

        Assertions.assertEquals(listCallsDTO,response.getBody());
        Assertions.assertEquals(200,response.getStatusCodeValue());

        // Checking when there is no calls
        Mockito.when(callsController.getCallsBetweenRangeOfDates(u,"2020-01-22","2020-02-22","223155111111",true)).thenReturn(new ArrayList<>());

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            clientController.getCallsInfoForUser(token,"2020-01-02","2020-02-22","223155111111",true);
        });

    }

    @Test
    public void getTop10DestinationCalledByUserTest() throws ResourceNotExistException  {
        // Test top 10 destinations called
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        String token = "123456";

        List<ITop10DestinationCalled> listItop10 = new ArrayList<>();
        listItop10.add(null);
        listItop10.add(null);
        System.out.println(listItop10.size());

        Mockito.when(sessionManager.getCurrentUser(token)).thenReturn(u);
        Mockito.when(callsController.getTop10DestinationWithNumberOfCalls(u)).thenReturn(listItop10);
        ResponseEntity<List<ITop10DestinationCalled>> response = clientController.getTop10DestinationCalledByUser(token);

        Assertions.assertEquals(listItop10,response.getBody());
        Assertions.assertEquals(200,response.getStatusCodeValue());

        // Test when there is no top 10 in the list
        Mockito.when(callsController.getTop10DestinationWithNumberOfCalls(u)).thenReturn(new ArrayList<>());
        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            clientController.getTop10DestinationCalledByUser(token);
        });
    }
}
