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
import utn.edu.tpfinal.controllers.CallsController;
import utn.edu.tpfinal.controllers.PhoneLineController;
import utn.edu.tpfinal.controllers.RatesController;
import utn.edu.tpfinal.controllers.UserController;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.models.UserType;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class BackofficeControllerTest {

    @InjectMocks
    private BackofficeController backofficeController;
    @Mock
    private UserController userController;
    @Mock
    private PhoneLineController phoneLineController;
    @Mock
    private RatesController ratesController;
    @Mock
    private CallsController callsController;
    @Mock
    private SessionManager sessionManager;
    @Mock
    private IReduceUser reduceUser;

    /**@GetMapping("/users/")
    public ResponseEntity<List<IReduceUser>> getUsers(@RequestHeader("Authorization") String sessionToken) throws ResourceNotExistException {
        try {
            User currentUser = sessionManager.getCurrentUser(sessionToken);
            List<IReduceUser> listAllUserProfiles =  userController.getUsers();

            if(listAllUserProfiles.size() > 0){
                return ResponseEntity.ok(listAllUserProfiles);
            }else{
                throw new ResourceNotExistException("There is no users in the database to retrieve yet.");
            }
        } catch (ResourceNotExistException e) {
            throw e;
        }
    }*/

    @Test
    public void getUsersTest() throws ResourceNotExistException {
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        String token = "asfsgegegeg";
        List<IReduceUser> list = new ArrayList<>();
        list.add(reduceUser);
        Mockito.when(sessionManager.getCurrentUser(token)).thenReturn(u);
        Mockito.when(userController.getUsers()).thenReturn(list);

        ResponseEntity<List<IReduceUser>> responseEntity = backofficeController.getUsers(token);

        Assertions.assertEquals(list, responseEntity.getBody());
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());

        Mockito.when(userController.getUsers()).thenReturn(new ArrayList<IReduceUser>());

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            backofficeController.getUsers(token);
        });

        doThrow(ResourceNotExistException.class)
                .when(sessionManager)
                .getCurrentUser("abcde");

        Assertions.assertThrows(ResourceNotExistException.class, () -> {
            backofficeController.getUsers("abcde");
        });

    }
}
