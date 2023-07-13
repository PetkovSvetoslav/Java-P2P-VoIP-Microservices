package com.example.p2pVoIP.controller;

import com.example.p2pVoIP.model.Message;
import com.example.p2pVoIP.service.MessageServiceImpl;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageServiceImpl messageServiceImpl;

    public MessageController(MessageServiceImpl messageServiceImpl) {
        this.messageServiceImpl = messageServiceImpl;
    }

    @GetMapping("/sender/{senderId}/receiver/{receiverId}")
    public List<Message> getMessagesBySenderIdAndReceiverId(@PathVariable Long senderId, @PathVariable Long receiverId) {
        return messageServiceImpl.getMessagesBySenderIdAndReceiverId(senderId, receiverId);
    }

    @GetMapping("/receiver/{receiverId}")
    public List<Message> getMessagesByReceiverId(@PathVariable Long receiverId) {
        return messageServiceImpl.getMessagesByReceiverId(receiverId);
    }

    @PostMapping
    public Message saveMessage(@RequestBody Message message) {
        return messageServiceImpl.saveMessage(message);
    }

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public Message send(Message message) throws Exception {
        Message savedMessage = messageServiceImpl.saveMessage(message);
        return new Message(null, savedMessage.getSenderId(), savedMessage.getReceiverId(), savedMessage.getContent(), null);
    }
}
