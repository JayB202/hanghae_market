package com.example.hanghae_market.entity;

import com.example.hanghae_market.dto.chat.ChatRequestDto;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ChatRoom chatRoom;

    @ManyToOne
    private User sender;

    private String message;

    public static Chat of(ChatRequestDto chatRequestDto, ChatRoom chatRoom, User sender) {
        return Chat.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .message(chatRequestDto.getMessage())
                .build();
    }
}
