package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.Exceptions.UserNotExistException;
import utn.edu.tpfinal.dto.BillForUserDTO;
import utn.edu.tpfinal.models.Bill;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.services.BillService;
import utn.edu.tpfinal.session.SessionManager;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

//import java.util.Date;

@Controller
public class BillController {
    private final BillService billService;
    private final SessionManager sessionManager;

    @Autowired
    public BillController(BillService billService, SessionManager sessionManager) {
        this.billService = billService;
        this.sessionManager = sessionManager;
    }

    // GET ONE BILL BY ID.
    public Optional<Bill> getBill(Integer idBill){
        return billService.getOneBill(idBill);
    }

    // GET ALL BILLS.
    public List<Bill> getBills(){
        return billService.getAllBills();
    }

    // POST BILL.
    public void addBill(Bill newBill){
        billService.addBill(newBill);
    }

    // DELETE ONE BILL BY ID.
    public void deleteProvince(Integer idBill){
        billService.deleteOneBill(idBill);
    }

    // UPDATE BILL BY ID.
    public void updateProvince(Bill bill, Integer idBill){
        billService.updateOneBill(bill, idBill);
    }

    // Get All bills between two ranges of dates
    public ResponseEntity<List<BillForUserDTO>> getBillsInfoForUser(@RequestHeader("Authorization") String sessionToken,
                                                                    @RequestParam(value = "fromDate", required = false) String fromDate,
                                                                    @RequestParam(value = "toDate", required = false) String toDate) throws UserNotExistException, ParseException {
        User currentUser = getCurrentUser(sessionToken);
        List<BillForUserDTO> billForUserDTO;

        if(fromDate != null && toDate != null){
            // We search bills between the dates.

            billForUserDTO= billService.getBillsBetweenRange(currentUser.getId(),fromDate,toDate);

            //Date from = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(fromDate);
            //Date to = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(toDate);
            //billForUserDTO= billService.getBillsBetweenRange(currentUser.getId(),from,to);
        }else{
            // we return all bills
            billForUserDTO = billService.getAllBillsForUserDTO();
        }

        return (billForUserDTO.size() > 0) ? ResponseEntity.ok(billForUserDTO) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private User getCurrentUser(String sessionToken) throws UserNotExistException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(UserNotExistException::new);
    }
}
