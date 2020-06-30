package utn.edu.tpfinal.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class BillServiceTest {




    /**
     public BillService(BillRepository billRepository) {
     this.billRepository = billRepository;
     }

     public Optional<Bill> getOneBill(Integer idBill) {
     return billRepository.findById(idBill);
     }

     public List<Bill> getAllBills() {
     return billRepository.findAll();
     }

     public void addBill(Bill newBill) {
     billRepository.save(newBill);
     }

     public void deleteOneBill(Integer idBill) {
     billRepository.deleteById(idBill);
     }

     public void updateOneBill(Bill newBill, Integer idBill) {
     Optional<Bill> resultBill = getOneBill(idBill);
     Bill currentBill = resultBill.get();

     if (resultBill != null) {
     currentBill.setIdBill(newBill.getIdBill());
     currentBill.setUser(newBill.getUser());
     currentBill.setTotalPrice(newBill.getTotalPrice());
     currentBill.setEmittionDate(newBill.getEmittionDate());
     currentBill.setExpirationDate(newBill.getExpirationDate());
     currentBill.setBillStatus(newBill.isBillStatus());
     currentBill.setTotalCost(newBill.getTotalCost());
     currentBill.setTotalProfit(newBill.getTotalProfit());
     currentBill.setCalls(newBill.getCalls());
     addBill(currentBill);
     }
     }

     public List<BillForUserDTO> getBillsBetweenRange(Integer idUser, String dateOne, String dateTwo) {

     // converting a string to a sql date
     Date dateFrom = Date.valueOf(dateOne);
     Date dateTo = Date.valueOf(dateTwo);


     // Convert the date to mysql type date
     //java.sql.Date from = new java.sql.Date(dateOne.getTime());
     //java.sql.Date to = new java.sql.Date(dateTwo.getTime());

     List<Bill> userBills = billRepository.getBillsFromUserBetweenDates(idUser, dateFrom, dateTo);
     List<BillForUserDTO> userDtoBills = new ArrayList<>();

     // we pass the info to the bill dto
     for (Bill b : userBills) {
     userDtoBills.add(new BillForUserDTO(b.getTotalPrice(), b.getEmittionDate(), b.getExpirationDate(), b.isBillStatus()));
     }

     return userDtoBills;
     }

     public List<BillForUserDTO> getBillsForUserDTO(Integer idUser) {
     // We get the user bills searching with its id.
     // Then we wrap them in a dto.

     List<Bill> userBills = billRepository.getUserBillInfo(idUser);
     List<BillForUserDTO> userDtoBills = new ArrayList<>();

     // we pass the info to the bill dto
     for (Bill b : userBills) {
     userDtoBills.add(new BillForUserDTO(b.getTotalPrice(), b.getEmittionDate(), b.getExpirationDate(), b.isBillStatus()));
     }

     return userDtoBills;
     }

     */
}
