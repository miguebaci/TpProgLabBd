package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.Exceptions.UserNotexistException;
import utn.edu.tpfinal.dto.BillForUserDTO;
import utn.edu.tpfinal.models.Bill;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.services.BillService;
import utn.edu.tpfinal.session.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//import java.util.Date;

@RestController
@RequestMapping("/bills")
public class BillController {
    private final BillService billService;
    private final SessionManager sessionManager;

    @Autowired
    public BillController(BillService billService, SessionManager sessionManager) {
        this.billService = billService;
        this.sessionManager = sessionManager;
    }

    // GET ONE BILL BY ID.
    @GetMapping("/{idBill}")
    public Optional<Bill> getBill(@PathVariable Integer idBill){
        return billService.getOneBill(idBill);
    }

    // GET ALL BILLS.
    @GetMapping("/")
    public List<Bill> getBills(){
        return billService.getAllBills();
    }

    // POST BILL.
    @PostMapping("/")
    public void addBill(@RequestBody Bill newBill){
        billService.addBill(newBill);
    }

    // DELETE ONE BILL BY ID.
    @DeleteMapping("/{idBill}")
    public void deleteProvince(@PathVariable Integer idBill){
        billService.deleteOneBill(idBill);
    }

    // UPDATE BILL BY ID.
    @PutMapping("/{idBill}")
    public void updateProvince(@RequestBody Bill bill, @PathVariable Integer idBill){
        billService.updateOneBill(bill, idBill);
    }

    // Get All bills between two ranges of dates
    @GetMapping("/userBillInfo")
    public ResponseEntity<List<BillForUserDTO>> getBillsInfoForUser(@RequestHeader("Authorization") String sessionToken,
                                                                    @RequestParam(value = "fromDate", required = false) String fromDate,
                                                                    @RequestParam(value = "toDate", required = false) String toDate) throws UserNotexistException, ParseException {
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

    private User getCurrentUser(String sessionToken) throws UserNotexistException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(UserNotexistException::new);
    }
}
