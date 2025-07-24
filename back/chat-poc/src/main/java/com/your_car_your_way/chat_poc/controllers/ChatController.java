package com.your_car_your_way.chat_poc.controllers;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.your_car_your_way.chat_poc.DTO.ChatMessage;
import com.your_car_your_way.chat_poc.models.Chat;
import com.your_car_your_way.chat_poc.repositories.ChatRepository;

// Le contrôleur gère les messages de chat
// Il reçoit les messages via WebSocket et les enregistre dans la base de données
// Ensuite, il envoie le message enregistré à tous les clients connectés au canal de chat
// Le point de terminaison pour envoyer des messages est "/app/chat.sendMessage"
@Controller
public class ChatController {

    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatRepository chatRepository, SimpMessagingTemplate messagingTemplate) {
        this.chatRepository = chatRepository;
        this.messagingTemplate = messagingTemplate;
    }

    // Cette méthode est appelée lorsqu'un message de chat est reçu
    // Elle enregistre le message dans la base de données et le diffuse à tous les clients
    // Le message est envoyé au canal "/support/chat"
    @MessageMapping("/chat.sendMessage")
    public void handleChatMessage(@Payload ChatMessage chatMessage, Principal principal) {

        String username = principal.getName();

        Chat chat = new Chat();
        chat.setContent(chatMessage.getContent());
        chat.setAuthorUsername(username);
        chat.setSentAt(Timestamp.valueOf(LocalDateTime.now()));
        Chat saved = chatRepository.save(chat);

        ChatMessage response = new ChatMessage();
        response.setId(saved.getId());
        response.setContent(saved.getContent());
        response.setAuthorUsername(saved.getAuthorUsername());
        response.setSentAt(saved.getSentAt().toLocalDateTime());

        messagingTemplate.convertAndSend("/support/chat", response);
    }
}
