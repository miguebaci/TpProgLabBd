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
import utn.edu.tpfinal.dto.BillForUserDTO;
import utn.edu.tpfinal.models.Bill;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.repositories.BillRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class BillServiceTest {

    @InjectMocks
    private BillService billService;
    @Mock
    private BillRepository billRepository;


    @Test
    public void getOneBillTest() {
        Bill bill = new Bill();
        Mockito.when(billRepository.findById(1)).thenReturn(Optional.of(bill));
        Optional<Bill> response = billService.getOneBill(1);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(bill, response.get());
    }

    @Test
    public void getAllBillsTest() {
        List<Bill> list = new ArrayList<>();
        list.add(new Bill());
        when(billRepository.findAll()).thenReturn(list);
        List<Bill> response = billService.getAllBills();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(list, response);
    }

    @Test
    public void addBillTest() {
        Bill b = new Bill();
        when(billRepository.save(b)).thenReturn(b);
        billService.addBill(b);
        verify(billRepository, times(1)).save(b);
    }

    @Test
    public void deleteOnePhoneLineTest() {
        Integer id = 1;
        doNothing().when(billRepository).deleteById(id);
        billService.deleteOneBill(id);

        Mockito.verify(billRepository, times(1)).deleteById(1);
    }

    @Test
    public void updateOneBillTest() {
        Bill b = new Bill(1, new User(), (float) 10.00, new java.sql.Date(2020), new java.sql.Date(2020), false, (float) 5.00, (float) 5.00, null);
        Bill updated = new Bill(1, new User(), (float) 20.00, new java.sql.Date(2020), new java.sql.Date(2020), false, (float) 5.00, (float) 5.00, null);
        when(billRepository.findById(1)).thenReturn(Optional.of(b));
        when(billRepository.save(b)).thenReturn(b);
        billService.updateOneBill(updated, 1);

        verify(billRepository, times(1)).findById(1);
        verify(billRepository, times(1)).save(updated);

        when(billService.getOneBill(1)).thenThrow(NoSuchElementException.class);
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            billService.getOneBill(1);
        });
    }


    @Test
    public void getBillsBetweenRangeTest() {
        Date dateFrom = Date.valueOf("2020-06-06");
        Date dateTo = Date.valueOf("2020-06-07");
        List<Bill> list = new ArrayList<>();
        List<BillForUserDTO> list2 = new ArrayList<>();
        list.add(new Bill(1, new User(), (float) 10.00, new java.sql.Date(2020), new java.sql.Date(2020), false, (float) 5.00, (float) 5.00, null));
        list2.add(new BillForUserDTO((float) 10.00, new java.sql.Date(2020), new java.sql.Date(2020), false));
        when(billRepository.getBillsFromUserBetweenDates(1, dateFrom, dateTo)).thenReturn(list);
        List<BillForUserDTO> response = billService.getBillsBetweenRange(1, "2020-06-06", "2020-06-07");
        Assertions.assertNotNull(list);
        Assertions.assertEquals(list2, response);
    }


    @Test
    public void getBillsForUserDTOTest() {
        List<Bill> list = new ArrayList<>();
        List<BillForUserDTO> list2 = new ArrayList<>();
        list.add(new Bill(1, new User(), (float) 10.00, new java.sql.Date(2020), new java.sql.Date(2020), false, (float) 5.00, (float) 5.00, null));
        list2.add(new BillForUserDTO((float) 10.00, new java.sql.Date(2020), new java.sql.Date(2020), false));
        when(billRepository.getUserBillInfo(1)).thenReturn(list);
        List<BillForUserDTO> response = billService.getBillsForUserDTO(1);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(list2, response);
    }
}
