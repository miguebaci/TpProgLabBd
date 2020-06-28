package utn.edu.tpfinal.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import utn.edu.tpfinal.Exceptions.NoDataAvailable;
import utn.edu.tpfinal.Exceptions.ResourceAlreadyExistException;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.controllers.CallsController;
import utn.edu.tpfinal.controllers.PhoneLineController;
import utn.edu.tpfinal.controllers.RatesController;
import utn.edu.tpfinal.controllers.UserController;
import utn.edu.tpfinal.dto.CallsForUserDTO;
import utn.edu.tpfinal.dto.UserResponseDTO;
import utn.edu.tpfinal.models.Rate;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.projections.IReduceUser;
import utn.edu.tpfinal.session.SessionManager;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/backoffice")
public class BackofficeController {

    private final UserController userController;
    private final PhoneLineController phoneLineController;
    private RatesController ratesController;
    private CallsController callsController;
    private final SessionManager sessionManager;

    @Autowired
    public BackofficeController(UserController userController, PhoneLineController phoneLineController, RatesController ratesController, CallsController callsController, SessionManager sessionManager) {
        this.userController = userController;
        this.phoneLineController = phoneLineController;
        this.ratesController = ratesController;
        this.callsController = callsController;
        this.sessionManager = sessionManager;
    }

    // GET ALL USERS. ( Hago que retorne una projection de todos los usuarios)
    @GetMapping("/")
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
    }

    // POST USER.
    @PostMapping("/")
    public ResponseEntity addUser(@RequestHeader("Authorization") String sessionToken, @RequestBody User newUser) throws NoSuchAlgorithmException, ResourceNotExistException, ResourceAlreadyExistException {
        ResponseEntity response;
        try {
            User currentUser = sessionManager.getCurrentUser(sessionToken);
            User user = userController.addUser(newUser);
            response = ResponseEntity.created(getLocation(user)).build();
        } catch (ResourceAlreadyExistException | NoSuchAlgorithmException | ResourceNotExistException e) {
            throw e;
        }
        return response;
    }

    // DELETE ONE USER BY ID.
    @DeleteMapping("/{idUser}")
    public ResponseEntity deleteUser(@RequestHeader("Authorization") String sessionToken, @PathVariable Integer idUser) throws ResourceNotExistException {
        try {
            User currentUser = sessionManager.getCurrentUser(sessionToken);
            userController.deleteUser(idUser);
            return ResponseEntity.ok().build();
        } catch (ResourceNotExistException e) {
            throw e;
        }
    }

    // SUSPEND OR REACTIVATE USER.
    @PutMapping("/activate/users/{idUser}")
    public ResponseEntity suspendUser(@RequestHeader("Authorization") String sessionToken, @PathVariable Integer idUser) throws ResourceNotExistException {
        try {
            User currentUser = sessionManager.getCurrentUser(sessionToken);
            userController.activeUser(idUser);
            return ResponseEntity.ok().build();
        }catch (ResourceNotExistException e){
            throw e;
        }
    }

    // SUSPEND OR REACTIVATE PHONELINE.
    @PutMapping("/activate/phoneline/{idPhone}")
    public ResponseEntity suspendphoneline(@RequestHeader("Authorization") String sessionToken, @PathVariable Integer idPhone) throws ResourceNotExistException {
        try{
            User currentUser = sessionManager.getCurrentUser(sessionToken);
            phoneLineController.activePhoneLine(idPhone);
            return ResponseEntity.ok().build();
        } catch(ResourceNotExistException e){
            throw e;
        }
    }

    // UPDATE USER.
    @PutMapping("/{idUser}")
    public ResponseEntity updateUser(@RequestHeader("Authorization") String sessionToken, @RequestBody User user, @PathVariable Integer idUser) throws NoSuchAlgorithmException, ResourceNotExistException {
        try {
            User currentUser = sessionManager.getCurrentUser(sessionToken);
            userController.updateUser(user, idUser);
            return ResponseEntity.ok().build();
        }catch(NoSuchAlgorithmException | ResourceNotExistException e) {
            throw e;
        }
    }

    @GetMapping("/rates")
    public ResponseEntity<Rate> getRatesByLocalities(@RequestHeader("Authorization") String sessionToken, @RequestParam(name = "localityOrigin") Integer idLocalityOrigin, @RequestParam(name = "localityDestiny") Integer idLocalityDestiny) throws ResourceNotExistException {
        try {
            User currentUser = sessionManager.getCurrentUser(sessionToken);
            return ResponseEntity.ok(this.ratesController.getRatesByLocality(idLocalityOrigin, idLocalityDestiny));
        }catch (ResourceNotExistException e){
            throw e;
        }
    }//localhost:8080/backoffice/rates?localityOrigin=223&localityDestiny=226

    @GetMapping("/calls/user/{idUser}")
    public ResponseEntity<List<CallsForUserDTO>> getCallsOfUser(@RequestHeader("Authorization") String sessionToken, @PathVariable Integer idUser, @RequestParam(value = "lineNumber") String lineNumber) throws ResourceNotExistException, NoDataAvailable {
        try {
            User currentUser = sessionManager.getCurrentUser(sessionToken);
            List<CallsForUserDTO>  listCallsDTO =  callsController.getCallsByUser(idUser, lineNumber);

            if (listCallsDTO.size() > 0) {
                return ResponseEntity.ok(listCallsDTO);
            } else {
                throw new NoDataAvailable("The user has no calls");
            }
        } catch (ResourceNotExistException | NoDataAvailable e) {
            throw e;
        }
    }//localhost:8080/backoffice/calls/user/{idUser}?lineNumber=


    // GET ONE REDUCE USER BY ID.
    @GetMapping("/userProfile/{idUser}")
    public ResponseEntity<IReduceUser> getReduceUser(@RequestHeader("Authorization") String sessionToken, @PathVariable Integer idUser) throws ResourceNotExistException {
        try {
            User currentUser = sessionManager.getCurrentUser(sessionToken);
            IReduceUser userProfile =  userController.getReduceUser(idUser);
            ResponseEntity<IReduceUser> responseEntity;

            if (userProfile != null) {
                responseEntity = ResponseEntity.ok(userProfile);
            } else {
                throw new ResourceNotExistException("We didnt find a user with the id you are trying to search.");
            }

            return responseEntity;

        } catch (ResourceNotExistException e) {
            throw e;
        }
    }


    // Response user with DTO
    @GetMapping("/fullUserProfile/{idUser}")
    public ResponseEntity<UserResponseDTO> getOneUserDTO(@RequestHeader("Authorization") String sessionToken, @PathVariable Integer idUser) throws SQLException, ResourceNotExistException {

        try {
            User currentUser = sessionManager.getCurrentUser(sessionToken);
            ResponseEntity<UserResponseDTO> responseEntity;
            //Get the dto of the user we are searching
            UserResponseDTO userResponseDTO = userController.getOneUserDTO(idUser);
            return ResponseEntity.ok(userResponseDTO);
        } catch (ResourceNotExistException e) {
            throw e;
        }
    }

    private URI getLocation(User user) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
    }

}