package utn.edu.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utn.edu.tpfinal.models.Bill;
import utn.edu.tpfinal.services.BillService;

import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {
    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping("/")
    public void addBill(@RequestBody Bill bill){
        billService.addBill(bill);
    }

    @GetMapping("/")
    public List<Bill> getAllBills(@RequestParam(required = false) Integer idBill){
        return billService.getAll(idBill);
    }
}
