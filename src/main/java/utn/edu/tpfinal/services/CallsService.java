package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.dto.CallsForUserDTO;
import utn.edu.tpfinal.models.Call;
import utn.edu.tpfinal.repositories.CallRepository;

import java.sql.Date;
import java.util.ArrayList;
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

    public List<CallsForUserDTO> geCallsBetweenRange(String from, String to, String lineNumber, Boolean caller) {

        // converting a string to a sql date
        java.sql.Date fromDate = java.sql.Date.valueOf(from);
        java.sql.Date toDate = Date.valueOf(to);

        List<Call> userCalls;
        List<CallsForUserDTO> listUserDtoCalls = new ArrayList<>();

        if(caller){
            userCalls = callRepository.getCallsFromUserAsCallerBetweenDates(fromDate, toDate, lineNumber);
        }else{
            userCalls = callRepository.getCallsFromUserAsReceiverBetweenDates(fromDate, toDate, lineNumber);
        }

        // we pass the information to the calls dto
        for(Call c: userCalls){
            Float price = null;

            if(caller){
                price = c.getPrice();
            }

            listUserDtoCalls.add(new CallsForUserDTO(price, c.getDateCall(), c.getHourCallFinish(),
                                                    c.getDuration(), c.getNumberOrigin(), c.getNumberDestiny()));
        }

        return listUserDtoCalls;
    }

    public List<CallsForUserDTO> getAllCallsForUserDTO(String lineNumber, Boolean caller) {
        List<Call> userCalls;
        List<CallsForUserDTO> listUserDtoCalls = new ArrayList<>();

        if(caller){
            userCalls = callRepository.getCallsFromUserAsCaller(lineNumber);
        }else{
            userCalls = callRepository.getCallsFromUserAsReceiver(lineNumber);
        }

        // we pass the information to the calls dto
        for(Call c: userCalls){
            Float price = null;

            if(caller){
                price = c.getPrice();
            }

            listUserDtoCalls.add(new CallsForUserDTO(price, c.getDateCall(), c.getHourCallFinish(),
                    c.getDuration(), c.getNumberOrigin(), c.getNumberDestiny()));
        }

        return listUserDtoCalls;
    }
}
