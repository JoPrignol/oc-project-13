package com.your_car_your_way.chat_poc.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.your_car_your_way.chat_poc.models.Chat;
import com.your_car_your_way.chat_poc.services.ChatService;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/support/chat")
    public Chat sendMessage(Chat message) {
        message.setSentAt(java.sql.Timestamp.valueOf(LocalDateTime.now()));
        return chatService.saveChat(message);
    }
}
