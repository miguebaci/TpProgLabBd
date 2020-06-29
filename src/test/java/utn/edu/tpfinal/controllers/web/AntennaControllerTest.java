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
import utn.edu.tpfinal.dto.CallsForUserDTO;
import utn.edu.tpfinal.models.Bill;
import utn.edu.tpfinal.models.Call;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.models.Rate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class AntennaControllerTest {
    @InjectMocks
    private AntennaController antennaController;
    @Mock
    private CallsController callsController;
    @Mock
    private CallsForUserDTO newCall;

    /**
     * public ResponseEntity<Call> makeCall(@RequestHeader("Authorization") String sessionToken, @RequestBody CallsForUserDTO newCall) throws ResourceNotExistException {
     * ResponseEntity response;
     * Call call = callsController.addCall(newCall);
     * if (call !=null) {
     * response = ResponseEntity.created(getLocation(call)).build();
     * }
     * else{
     * response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
     * }
     * return response;
     * }
     * <p>
     * private URI getLocation(Call call) {
     * return ServletUriComponentsBuilder
     * .fromCurrentRequest()
     * .path("/{id}")
     * .buildAndExpand(call.getIdCall())
     * .toUri();
     * }
     *//*
    @Test
    public void makeCallTest() throws ResourceNotExistException, URISyntaxException {
        Date date = new Date();
        String token = "OaBB";
        URI uri = new URI("localhost:8080/antenna/call/");
        CallsForUserDTO callDto = new CallsForUserDTO(date, 1, "2235031234", "2235001234");
        Call call = new Call(1, new PhoneLine(), new PhoneLine(), new Bill(), new Rate(), (float) 15.00, (float) 5.00, (float) 10.00, date, date, 1, "2235031234", "2235001234");
        Mockito.when(callsController.addCall(callDto)).thenReturn(call);
        Mockito.when(antennaController.getLocation(call)).thenReturn(uri);
        ResponseEntity<Call> responseEntity = antennaController.makeCall(token, callDto);
        Assertions.assertEquals(call, responseEntity.getBody());
        Assertions.assertEquals(201, responseEntity.getStatusCode());
    }*/
}
