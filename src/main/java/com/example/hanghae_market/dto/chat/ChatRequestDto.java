package com.example.hanghae_market.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestDto {
    private String chatRoomId;
    private String message;
    private String sender;

}