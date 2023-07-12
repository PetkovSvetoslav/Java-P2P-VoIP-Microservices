package com.example.p2pVoIP.service;

import com.example.p2pVoIP.model.Message;

import java.util.List;

public interface MessageService {
    List<Message> getMessagesBySenderIdAndReceiverId(Long senderId, Long receiverId);
    List<Message> getMessagesByReceiverId(Long receiverId);
    Message saveMessage(Message message);


}
