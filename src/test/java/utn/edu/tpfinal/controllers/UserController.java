package utn.edu.tpfinal.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;
import utn.edu.tpfinal.dto.CallsByDayDTO;
import utn.edu.tpfinal.dto.CallsByUserOnDayX;

import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.services.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.util.DateUtil.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class UserController {


    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;


    /**
     * @GetMapping("/reduce/{idUser}{Date}")
     *     public ResponseEntity<CallsByUserOnDayX> getUserCallsByDayDTO (@PathVariable Integer idUser, Date date) throws SQLException {
     *         ResponseEntity<CallsByUserOnDayX> responseEntity;
     *
     *         // Get the dto of the user
     *         CallsByUserOnDayX callsByUserOnDayX = userService.getCallsByUserOnDayXDto(idUser, date);
     *
     *         if(callsByUserOnDayX != null) {
     *             responseEntity = ResponseEntity.ok(callsByUserOnDayX);
     *         }else{
     *             responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
     *         }
     *         return responseEntity;
     *     }
     */
   /* @Test
    public void getUserCallsByDayDTO(){
        List<CallsByDayDTO>list=new ArrayList();
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        list.add(new CallsByDayDTO(1,"123","456"));
        list.add(new CallsByDayDTO(1,"123","456"));
        list.add(new CallsByDayDTO(1,"123","456"));
        CallsByUserOnDayX user = new CallsByUserOnDayX(1,"nana", list);
        when(userService.getCallsByUserOnDayXDto(1, date)).thenReturn(user);
        ResponseEntity<CallsByUserOnDayX> response = userController.getUserCallsByDayDTO(user.getId(),date);

        assertNotNull(response);
        assertEquals(list, response);
    }*/
}

