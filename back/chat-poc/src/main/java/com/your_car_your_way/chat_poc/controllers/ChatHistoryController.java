package com.your_car_your_way.chat_poc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.your_car_your_way.chat_poc.models.Chat;
import com.your_car_your_way.chat_poc.services.ChatService;

//TODO: Ajouter l'url dans les variables d'environnement

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/chats")
public class ChatHistoryController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public List<Chat> getAllChats() {
        return chatService.getAllChats();
    }

    @PostMapping
    public Chat saveChat(@RequestBody Chat chat) {
        return chatService.saveChat(chat);
    }
}
