package com.example.hanghae_market.dto.chat;

import com.example.hanghae_market.entity.ChatRoom;
import com.example.hanghae_market.entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatRoomListResponseDto {
    private String chatRoomId;
    private String interlocutor;
    private Long postId;
    private String postTitle;
    private String lastChat;
    private String time;
    private int unreadChat;


    public static ChatRoomListResponseDto.ChatRoomListResponseDtoBuilder of(ChatRoom chatRoom, User interlocutor) {
        return ChatRoomListResponseDto.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .interlocutor(interlocutor.getUserId());
//                .postId(chatRoom.getPost().getPostId())
//                .postTitle(chatRoom.getPost().getPostTitle().toString());
    }
}
