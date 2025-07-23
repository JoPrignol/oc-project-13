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

@Controller
public class ChatController {

    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatRepository chatRepository, SimpMessagingTemplate messagingTemplate) {
        this.chatRepository = chatRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    public void handleChatMessage(@Payload ChatMessage chatMessage, Principal principal) {

        String username = principal.getName();

        Long authorId = 1L;

        Chat chat = new Chat();
        chat.setContent(chatMessage.getContent());
        chat.setAuthorId(authorId);
        chat.setAuthorUsername(username);
        chat.setSentAt(Timestamp.valueOf(LocalDateTime.now()));
        Chat saved = chatRepository.save(chat);

        ChatMessage response = new ChatMessage();
        response.setId(saved.getId());
        response.setContent(saved.getContent());
        response.setAuthorId(saved.getAuthorId());
        response.setAuthorUsername(username);
        response.setAuthorUsername(saved.getAuthorUsername());
        response.setSentAt(saved.getSentAt().toLocalDateTime());

        messagingTemplate.convertAndSend("/support/chat", response);
    }
}
