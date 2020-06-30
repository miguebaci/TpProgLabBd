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
import utn.edu.tpfinal.dto.BillForUserDTO;
import utn.edu.tpfinal.models.Bill;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.models.UserType;
import utn.edu.tpfinal.services.BillService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class BillControllerTest {
    @InjectMocks
    private BillController billController;

    @Mock
    private BillService billService;

    @Test
    public void getBillTest() {
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        Bill b = new Bill(null, u, (float)150.2, Date.valueOf("2020-10-22"), Date.valueOf("2020-10-22"), false, (float)100, (float)50,null);
        Mockito.when(billService.getOneBill(b.getIdBill())).thenReturn(Optional.ofNullable(b));
        Optional<Bill> response =  billController.getBill(b.getIdBill());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(b,response.get());
    }

    @Test
    public void getBillsTest() {
        List<Bill> myBills = new ArrayList<>();
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        Bill b = new Bill(null, u, (float)150.2, Date.valueOf("2020-10-22"), Date.valueOf("2020-10-22"), false, (float)100, (float)50,null);
        Bill b1 = new Bill(null, u, (float)150.2, Date.valueOf("2020-01-22"), Date.valueOf("2020-02-22"), false, (float)100, (float)50,null);
        myBills.add(b);
        myBills.add(b1);

        Mockito.when(billService.getAllBills()).thenReturn(myBills);
        List<Bill> response =  billController.getBills();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(myBills,response);
    }

    @Test
    public void addBillTest() {
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        Bill b = new Bill(null, u, (float)150.2, Date.valueOf("2020-10-22"), Date.valueOf("2020-10-22"), false, (float)100, (float)50,null);
        Mockito.doNothing().when(billService).addBill(b);
        billController.addBill(b);
        Mockito.verify(billService, Mockito.times(1)).addBill(b);
    }

    @Test
    public void deleteBillTest () {
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        Bill b = new Bill(null, u, (float)150.2, Date.valueOf("2020-10-22"), Date.valueOf("2020-10-22"), false, (float)100, (float)50,null);
        Mockito.doNothing().when(billService).deleteOneBill(b.getIdBill());
        billController.deleteBill(b.getIdBill());
        Mockito.verify(billService, Mockito.times(1)).deleteOneBill(b.getIdBill());
    }

    @Test
    public void updateBillTest () {
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        Bill b = new Bill(null, u, (float)150.2, Date.valueOf("2020-10-22"), Date.valueOf("2020-10-22"), false, (float)100, (float)50,null);
        Mockito.doNothing().when(billService).updateOneBill(b, b.getIdBill());
        billController.updateBill(b, b.getIdBill());
        Mockito.verify(billService, Mockito.times(1)).updateOneBill(b, b.getIdBill());
    }

    @Test
    public void getBillsBetweenRangeOfDatesTest () throws ResourceNotExistException {
        // Verify bills between range
        User u = new User(1, UserType.client,37867266,"dema","felipe","demaria","dema22",false,null,null);
        List<BillForUserDTO> listBillsDTO = new ArrayList<>();
        List<BillForUserDTO> listWithAllBills = new ArrayList<>();

        BillForUserDTO bill = new BillForUserDTO((float) 100.2, Date.valueOf("2020-10-22"), Date.valueOf("2020-11-22"), false);
        BillForUserDTO billTwo = new BillForUserDTO((float) 100.2, Date.valueOf("2020-10-22"), Date.valueOf("2020-11-22"), false);
        BillForUserDTO billThree = new BillForUserDTO((float) 100.2, Date.valueOf("2020-01-22"), Date.valueOf("2020-02-22"), false);
        listBillsDTO.add(bill);
        listBillsDTO.add(billTwo);

        Mockito.when(billService.getBillsBetweenRange(u.getId(), "2020-10-22", "2020-11-22")).thenReturn(listBillsDTO);
        List<BillForUserDTO> response =  billController.getBillsBetweenRangeOfDates(u,"2020-10-22","2020-11-22");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(listBillsDTO,response);

        // Verify when there is no bills in the range
        listWithAllBills.add(bill);
        listWithAllBills.add(billTwo);
        listWithAllBills.add(billThree);

        Mockito.when(billService.getBillsForUserDTO(u.getId())).thenReturn(listWithAllBills);
        List<BillForUserDTO> responseTwo =  billController.getBillsBetweenRangeOfDates(u,null,null);
        Assertions.assertNotNull(responseTwo);
        Assertions.assertEquals(listWithAllBills,responseTwo);
    }
}
