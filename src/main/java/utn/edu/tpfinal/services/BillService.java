package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.models.Bill;
import utn.edu.tpfinal.repositories.BillRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Optional<Bill> getOneBill(Integer idBill) {
        return billRepository.findById(idBill);
    }

    public List<Bill> getAllBills(){
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

        if(resultBill != null) {
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

}
