package com.example.hanghae_market.dto.chat;

import com.example.hanghae_market.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class ChatRoomResponseDto {
    private List<ChatResponseDto> chatMessages = new ArrayList<>();
    private String chatRoomName;

    public static ChatRoomResponseDto of(ChatRoom chatRoom){
        return ChatRoomResponseDto.builder()
                .chatMessages(chatRoom.getChatMessages().stream().map(ChatResponseDto::of).toList())
//                .chatRoomName(chatRoom.getPost().getPostTitle())
                .build();
    }
}
