package com.example.p2pVoIP.service;


import com.example.p2pVoIP.model.Call;
import com.example.p2pVoIP.repository.CallRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CallServiceImpl implements  CallService{

    private final CallRepository callRepository;

    public CallServiceImpl(CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    public Call createCall(Call call) {
        return callRepository.save(call);
    }

    public Optional<Call> getCallById(Long id) {
        return callRepository.findById(id);
    }

    public List<Call> getAllCalls() {
        return callRepository.findAll();
    }

    public Call updateCall(Call call) {
        if(callRepository.existsById(call.getId())) {
            return callRepository.save(call);
        }
        else {
            throw new RuntimeException("Call with id " + call.getId() + " does not exist");
        }
    }

    public void deleteCall(Long id) {
        if(callRepository.existsById(id)) {
            callRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Call with id " + id + " does not exist");
        }
    }
}
