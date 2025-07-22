package com.your_car_your_way.chat_poc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.your_car_your_way.chat_poc.models.Chat;
import com.your_car_your_way.chat_poc.repositories.ChatRepository;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public List<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    public Chat saveChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
