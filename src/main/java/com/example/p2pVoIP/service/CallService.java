package com.example.p2pVoIP.service;

import com.example.p2pVoIP.model.Call;

import java.util.List;
import java.util.Optional;

public interface CallService {
    Call createCall(Call call);
    Optional<Call> getCallById(Long id);
    List<Call> getAllCalls();
    Call updateCall(Call call);
    void deleteCall(Long id);
}
