package com.example.p2pVoIP.repository;

import com.example.p2pVoIP.model.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call, Long> {
    List<Call> findByCallerId(Long callerId);

    List<Call> findByReceiverId(Long receiverId);

    List<Call> findByStatus(Call.CallStatus status);
}
