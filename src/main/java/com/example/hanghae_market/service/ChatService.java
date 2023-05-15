package com.example.hanghae_market.service;

import com.example.hanghae_market.customData.CustomMessage;
import com.example.hanghae_market.dto.ResponseDto;
import com.example.hanghae_market.dto.chat.ChatRequestDto;
import com.example.hanghae_market.dto.chat.ChatRoomResponseDto;
import com.example.hanghae_market.entity.Chat;
import com.example.hanghae_market.entity.ChatRoom;
import com.example.hanghae_market.entity.User;
import com.example.hanghae_market.repository.ChatRepository;
import com.example.hanghae_market.repository.ChatRoomRepository;
import com.example.hanghae_market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Transactional
    public User createChat(String chatRoomId, ChatRequestDto chatRequestDto) {
        ChatRoom chatRoom = chatRoomRepository.findByChatRoomId(chatRoomId).orElseThrow(() -> new RuntimeException("존재하지 않는 채팅방입니다."));
        User sender = userRepository.findByUserId(chatRequestDto.getSender()).orElseThrow(()-> new RuntimeException("존재하지 않는 유저입니다."));

        Chat chat = Chat.of(chatRequestDto, chatRoom, sender);
        chatRepository.save(chat);

        setChatCount(sender, chatRoom);
        setExited(sender,chatRoom);

        return getInterlocutor(sender, chatRoom);
    }



    private void setChatCount(User sender, ChatRoom chatRoom){
        if(chatRoom.isSeller(sender)){
            chatRoom.setSellerChatCount(chatRoom.getSellerChatCount()+1);
        }else{
            chatRoom.setBuyerChatCount(chatRoom.getBuyerChatCount()+1);
        }
    }

    private void setExited(User sender, ChatRoom chatRoom){
        if(chatRoom.isSeller(sender)){
            chatRoom.setBuyerExited(false);
        }
        else{
            chatRoom.setSellerExited(false);
        }
    }

    private User getInterlocutor(User sender, ChatRoom chatRoom){
        return chatRoom.isSeller(sender) ? chatRoom.getBuyer() : chatRoom.getSeller();
    }




}
