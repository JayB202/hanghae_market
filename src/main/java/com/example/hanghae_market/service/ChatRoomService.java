package com.example.hanghae_market.service;

import com.example.hanghae_market.dto.ResponseDto;
import com.example.hanghae_market.dto.chat.ChatRoomListResponseDto;
import com.example.hanghae_market.entity.Chat;
import com.example.hanghae_market.entity.ChatRoom;
import com.example.hanghae_market.entity.Post;
import com.example.hanghae_market.entity.User;
import com.example.hanghae_market.repository.ChatRoomRepository;
import com.example.hanghae_market.repository.PostRepository;
import com.example.hanghae_market.util.ChatTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.example.hanghae_market.customData.CustomMessage.*;
import static org.springframework.data.util.Pair.of;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final PostRepository postRepository;

    @Transactional
    public String createChatRoom(Long postId, User user) {
        Post post = postRepository.findByPostId(postId).orElseThrow(() -> new NoSuchElementException("POST_NOT_FOUND"));
        if(post.getUser().getUserId().equals(user.getUserId())){
            throw new RuntimeException("CAN_NOT_CHAT_WITH_YOURSELF");
        }
        ChatRoom chatRoom = ChatRoom.of(post, user);
        chatRoomRepository.save(chatRoom);
        readChat(chatRoom);

        return chatRoom.getChatRoomId();
    }

    @Transactional
    public ResponseEntity<ResponseDto> getChatRoomList(User user) {
        List<ChatRoom> chatRoomList = chatRoomRepository.findAllByBuyerIdOrSellerID(user.getUserId(), user.getUserId());
        List<ChatRoomListResponseDto> chatRoomListResponseDtoList = new ArrayList<>();
        for(ChatRoom chatRoom : chatRoomList){
            if(isExited(user,chatRoom)){
                continue;
            }
            ChatRoomListResponseDto.ChatRoomListResponseDtoBuilder chatRoomListResponseDtoBuilder = ChatRoomListResponseDto.of(chatRoom, getInterlocutor(user, chatRoom));
            if(!chatRoom.getChatMessages().isEmpty()){
                Chat lastChat = chatRoom.getChatMessages().get(chatRoom.getChatMessages().size() -1);
                chatRoomListResponseDtoBuilder.lastChat(lastChat.getMessage()).time(ChatTime.chatTime(lastChat.getCreatedAt())).unreadChat(getUnreadChat(user, chatRoom));
            }
            chatRoomListResponseDtoList.add(chatRoomListResponseDtoBuilder.build());
        }
        return ResponseDto.toResponseEntity(SUEEST_TO_GET_CHAT_ROOM_LIST, chatRoomListResponseDtoList);
    }

    @Transactional
    public ResponseEntity<ResponseDto> exitChatRoom(String chatRoomId, User user) {
        ChatRoom chatRoom = chatRoomRepository.findByChatRoomId(chatRoomId).orElseThrow(() -> new NoSuchElementException("CHAT_ROOM_NOT_FOUND"));
        exitRoom(user, chatRoom);

        return ResponseDto.toResponseEntity(CHAT_ROOM_EXIT_SUCCESS);
    }


    private void readChat(ChatRoom chatRoom) {
        chatRoom.initSellerChatCount();
    }

    private boolean isExited(User user, ChatRoom room) {
        return (room.isSeller(user) && room.isSellerExited()) || (!room.isSeller(user) && room.isBuyerExited());
    }

    private User getInterlocutor(User user, ChatRoom chatRoom) {
        return chatRoom.isSeller(user) ? chatRoom.getBuyer() : chatRoom.getSeller();
    }

    private int getUnreadChat(User user, ChatRoom room) {
        return room.isSeller(user) ? room.getBuyerChatCount() : room.getSellerChatCount();
    }

    private void exitRoom(User user, ChatRoom chatRoom) {
        if (chatRoom.isSeller(user)) {
            chatRoom.setSellerExited(true);
        } else {
            chatRoom.setBuyerExited(true);
        }
        if (chatRoom.isSellerExited() && chatRoom.isBuyerExited()) {
            chatRoomRepository.deleteById(chatRoom.getChatRoomId());
        }
    }

}
