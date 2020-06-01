package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.Bill;
import utn.edu.tpfinal.services.BillService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bills")
public class BillController {
    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
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
}
