package com.example.hanghae_market.controller;

import com.example.hanghae_market.dto.chat.ChatRequestDto;
import com.example.hanghae_market.dto.chat.ChatResponseDto;
import com.example.hanghae_market.entity.User;
import com.example.hanghae_market.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;
//    private final SseService sseService;

    @MessageMapping("/{chatRoomId}")
    @SendTo("/sub/{chatRoomId}")
    public void createChat(@DestinationVariable String chatRoomId, ChatRequestDto chatRequestDto){
        User receiver = chatService.createChat(chatRoomId, chatRequestDto);
        simpMessagingTemplate.convertAndSend("/sub/"+chatRoomId, ChatResponseDto.of(chatRequestDto));

    }

}
