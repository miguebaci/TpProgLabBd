package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.models.Bill;
import utn.edu.tpfinal.repositories.BillRepository;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public void addBill(Bill bill) {
        billRepository.save(bill);
    }

    public List<Bill> getAll(Integer idBill) {
        if(isNull(idBill)){
            return billRepository.findAll();
        }
        return billRepository.findOneByBillId(idBill);
    }

}
