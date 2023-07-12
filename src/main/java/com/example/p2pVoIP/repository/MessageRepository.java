package com.example.p2pVoIP.repository;

import com.example.p2pVoIP.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByReceiverId(Long receiverId);

    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}