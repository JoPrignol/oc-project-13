package com.your_car_your_way.chat_poc.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.your_car_your_way.chat_poc.DTO.ChatMessage;
import com.your_car_your_way.chat_poc.models.Chat;
import com.your_car_your_way.chat_poc.services.ChatService;

// Ce contrôleur gère l'historique des chats
// Il permet de récupérer tous les messages de chat en DB
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/chats")
public class ChatHistoryController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public List<ChatMessage> getAllChats() {
        List<Chat> chats = chatService.getAllChats();

        return chats.stream().map(chat -> {
            ChatMessage dto = new ChatMessage();
            dto.setId(chat.getId());
            dto.setContent(chat.getContent());
            dto.setAuthorUsername(chat.getAuthorUsername());
            dto.setSentAt(chat.getSentAt().toLocalDateTime());
            return dto;
        }).collect(Collectors.toList());
    }
}
