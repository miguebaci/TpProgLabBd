package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.dto.BillForUserDTO;
import utn.edu.tpfinal.models.Bill;
import utn.edu.tpfinal.models.User;
import utn.edu.tpfinal.services.BillService;
import utn.edu.tpfinal.session.SessionManager;

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
    public Optional<Bill> getBill(Integer idBill) {
        return billService.getOneBill(idBill);
    }

    // GET ALL BILLS.
    public List<Bill> getBills() {
        return billService.getAllBills();
    }

    // POST BILL.
    public void addBill(Bill newBill) {
        billService.addBill(newBill);
    }

    // DELETE ONE BILL BY ID.
    public void deleteProvince(Integer idBill) {
        billService.deleteOneBill(idBill);
    }

    // UPDATE BILL BY ID.
    public void updateProvince(Bill bill, Integer idBill) {
        billService.updateOneBill(bill, idBill);
    }

    // Get All bills between two ranges of dates
    public List<BillForUserDTO> getBillsBetweenRangeOfDates(User currentUser, String fromDate, String toDate) throws ResourceNotExistException {

        List<BillForUserDTO> billForUserDTO;

        if (fromDate != null && toDate != null) {
            // We search bills between the dates.
            billForUserDTO = billService.getBillsBetweenRange(currentUser.getId(), fromDate, toDate);
        } else {
            // we return all bills
            billForUserDTO = billService.getBillsForUserDTO(currentUser.getId());
        }
        return billForUserDTO;
    }
}
