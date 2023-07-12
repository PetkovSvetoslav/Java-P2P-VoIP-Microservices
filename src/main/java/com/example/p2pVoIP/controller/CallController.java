package com.example.p2pVoIP.controller;

import com.example.p2pVoIP.model.Call;
import com.example.p2pVoIP.service.CallServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/calls")
public class CallController {

    @Autowired
    private CallServiceImpl callServiceImpl;

    @PostMapping
    public ResponseEntity<Call> createCall(@RequestBody Call call) {
        Call createdCall = callServiceImpl.createCall(call);
        return new ResponseEntity<>(createdCall, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Call> getCallById(@PathVariable Long id) {
        Call call = callServiceImpl.getCallById(id)
                .orElseThrow(() -> new RuntimeException("Call with id " + id + " not found"));
        return ResponseEntity.ok(call);
    }
    @GetMapping
    public ResponseEntity<List<Call>> getAllCalls() {
        List<Call> calls = callServiceImpl.getAllCalls();
        return ResponseEntity.ok(calls);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Call> updateCall(@PathVariable Long id, @RequestBody Call call) {
        call.setId(id);
        Call updatedCall = callServiceImpl.updateCall(call);
        return ResponseEntity.ok(updatedCall);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCall(@PathVariable Long id) {
        callServiceImpl.deleteCall(id);
        return ResponseEntity.noContent().build();
    }
}
