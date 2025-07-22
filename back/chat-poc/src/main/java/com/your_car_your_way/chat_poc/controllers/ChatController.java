package com.your_car_your_way.chat_poc.controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.your_car_your_way.chat_poc.DTO.ChatMessage;
import com.your_car_your_way.chat_poc.models.Chat;
import com.your_car_your_way.chat_poc.repositories.ChatRepository;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatRepository chatRepository;

    public ChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @PostMapping("/send")
    public ChatMessage sendMessage(@RequestBody ChatMessage chatMessage) {
        // Récupérer le nom d'utilisateur connecté
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Simuler un authorId pour le POC
        Long authorId = 1L;

        // Mapper le DTO vers l'entité
        Chat chat = new Chat();
        chat.setContent(chatMessage.getContent());
        chat.setAuthorId(authorId);
        chat.setSentAt(Timestamp.valueOf(LocalDateTime.now()));

        // Sauvegarder
        Chat saved = chatRepository.save(chat);

        // Retourner un DTO avec les infos sauvegardées
        ChatMessage response = new ChatMessage();
        response.setId(saved.getId());
        response.setContent(saved.getContent());
        response.setAuthorId(saved.getAuthorId());
        response.setAuthorUsername(username);
        response.setSentAt(saved.getSentAt().toLocalDateTime());

        return response;
    }

    @GetMapping("/me")
    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
