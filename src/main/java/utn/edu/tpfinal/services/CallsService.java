package utn.edu.tpfinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.edu.tpfinal.models.Call;
import utn.edu.tpfinal.repositories.CallRepository;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class CallsService {
    private final CallRepository callRepository;

    @Autowired
    public CallsService(CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    public void addCall(Call call) {
        callRepository.save(call);
    }

    public List<Call> getAll(Integer idCall) {
        if(isNull(idCall)){
            return callRepository.findAll();
        }
        return callRepository.findOneByCallId(idCall);
    }

}
