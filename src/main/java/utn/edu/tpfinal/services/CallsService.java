package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.dto.CallForUserDTO;
import utn.edu.tpfinal.models.Bill;
import utn.edu.tpfinal.models.Call;
import utn.edu.tpfinal.repositories.CallRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CallsService {
    private final CallRepository callRepository;

    @Autowired
    public CallsService(CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    public Optional<Call> getOneCall(Integer idCall) {
        return callRepository.findById(idCall);
    }

    public List<Call> getAllCalls(){
        return callRepository.findAll();
    }

    public void addCall(Call newCall) {
        callRepository.save(newCall);
    }

    public void deleteOneCall(Integer idCall) {
        callRepository.deleteById(idCall);
    }

    public void updateOneCall(Call newCall, Integer idCall) {
        Optional<Call> resultCall = getOneCall(idCall);
        Call currentCall = resultCall.get();

        if(resultCall != null) {
            currentCall.setIdCall(newCall.getIdCall());
            currentCall.setLineOrigin(newCall.getLineOrigin());
            currentCall.setLineDestiny(newCall.getLineDestiny());
            currentCall.setBill(newCall.getBill());
            currentCall.setRate(newCall.getRate());
            currentCall.setPrice(newCall.getPrice());
            currentCall.setCost(newCall.getCost());
            currentCall.setProfit(newCall.getProfit());
            currentCall.setDateCall(newCall.getDateCall());
            currentCall.setHourCallFinish(newCall.getHourCallFinish());
            currentCall.setDuration(newCall.getDuration());
            currentCall.setNumberOrigin(newCall.getNumberOrigin());
            currentCall.setNumberDestiny(newCall.getNumberDestiny());
            addCall(currentCall);
        }
    }

    public List<CallForUserDTO> geCallsBetweenRange(Integer id, Date from, Date to) {
        return null;
    }

    public List<CallForUserDTO> getAllCallsForUserDTO() {
        return null;
    }
}
