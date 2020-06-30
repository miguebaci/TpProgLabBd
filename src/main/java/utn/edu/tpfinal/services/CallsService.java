package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.Exceptions.ResourceNotExistException;
import utn.edu.tpfinal.dto.CallsForUserDTO;
import utn.edu.tpfinal.dto.Top10DestinationCalledDTO;
import utn.edu.tpfinal.models.Call;
import utn.edu.tpfinal.models.PhoneLine;
import utn.edu.tpfinal.projections.ITop10DestinationCalled;
import utn.edu.tpfinal.repositories.CallRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CallsService {
    private final CallRepository callRepository;
    private final PhoneLineService phoneLineService;

    @Autowired
    public CallsService(CallRepository callRepository, PhoneLineService phoneLineService) {
        this.callRepository = callRepository;
        this.phoneLineService = phoneLineService;
    }

    public Optional<Call> getOneCall(Integer idCall) {
        return callRepository.findById(idCall);
    }

    public List<Call> getAllCalls() {
        return callRepository.findAll();
    }

    public Call addCall(CallsForUserDTO callDto) throws ResourceNotExistException {
        PhoneLine from = phoneLineService.getByLineNumber(callDto.getNumberOrigin());
        PhoneLine to = phoneLineService.getByLineNumber(callDto.getNumberDestiny());
        Call call = new Call();
        if (!from.getSuspended() && !to.getSuspended()) {
            if (callDto.getDuration() > 0) {
                call.setDateCall(callDto.getDateCall());
                call.setDuration(callDto.getDuration());
                call.setNumberOrigin(callDto.getNumberOrigin());
                call.setNumberDestiny(callDto.getNumberDestiny());
                return callRepository.save(call);
            }
            else return call;
        }
        else throw new ResourceNotExistException("Verify suspension of the phonelines.");
    }

    public List<CallsForUserDTO> getCallsBetweenRange(String from, String to, String lineNumber, Boolean caller) {

        // converting a string to a sql date
        java.sql.Date fromDate = java.sql.Date.valueOf(from);
        java.sql.Date toDate = Date.valueOf(to);

        List<Call> userCalls;
        List<CallsForUserDTO> listUserDtoCalls = new ArrayList<>();

        if (caller) {
            userCalls = callRepository.getCallsFromUserAsCallerBetweenDates(fromDate, toDate, lineNumber);
        } else {
            userCalls = callRepository.getCallsFromUserAsReceiverBetweenDates(fromDate, toDate, lineNumber);
        }

        // we pass the information to the calls dto
        for (Call c : userCalls) {
            Float price = null;

            if (caller) {
                price = c.getPrice();
            }

            listUserDtoCalls.add(new CallsForUserDTO(c.getDateCall(),
                    c.getDuration(), c.getNumberOrigin(), c.getNumberDestiny()));
        }

        return listUserDtoCalls;
    }

    public List<CallsForUserDTO> getCallsForUserDTO(String lineNumber, Boolean caller) {
        List<Call> userCalls;
        List<CallsForUserDTO> listUserDtoCalls = new ArrayList<>();

        if (caller) {
            userCalls = callRepository.getCallsFromUserAsCaller(lineNumber);
        } else {
            userCalls = callRepository.getCallsFromUserAsReceiver(lineNumber);
        }

        // we pass the information to the calls dto
        for (Call c : userCalls) {
            Float price = null;

            if (caller) {
                price = c.getPrice();
            }

            listUserDtoCalls.add(new CallsForUserDTO(c.getDateCall(),
                    c.getDuration(), c.getNumberOrigin(), c.getNumberDestiny()));
        }

        return listUserDtoCalls;
    }


    public List<ITop10DestinationCalled> getTop10DestinationsWithNumberOfCalls(Integer idUser){
        List<ITop10DestinationCalled> list = callRepository.getTop10Info(idUser);
        return list;
    }

}
