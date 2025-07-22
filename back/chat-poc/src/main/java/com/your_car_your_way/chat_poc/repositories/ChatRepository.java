package com.your_car_your_way.chat_poc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.your_car_your_way.chat_poc.models.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
