package com.example.hanghae_market.dto.chat;

import com.example.hanghae_market.entity.Chat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class ChatResponseDto {

    private String sender;
    private String message;
    private String chatTime;
    public static ChatResponseDto  of(ChatRequestDto chatRequestDto) {
        return ChatResponseDto.builder()
                .sender(chatRequestDto.getSender())
                .message(chatRequestDto.getMessage())
                .chatTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
                .build();
    }

    public static ChatResponseDto of(Chat message) {
        return ChatResponseDto.builder()
                .sender(message.getSender().getUserId())
                .message(message.getMessage())
                .chatTime(message.getCreatedAt().format(DateTimeFormatter.ofPattern("HH:mm")))
                .build();
    }
}
