package com.example.hanghae_market.controller;

import com.example.hanghae_market.dto.ResponseDto;
import com.example.hanghae_market.security.UserDetailsImpl;
import com.example.hanghae_market.service.ChatRoomService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping("/room/{postId}")
    public String createChatRoom(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return chatRoomService.createChatRoom(postId, userDetailsImpl.getUser());
    }

    @GetMapping("/rooms")
    public ResponseEntity<ResponseDto> chatRoomList(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return chatRoomService.getChatRoomList(userDetailsImpl.getUser());
    }

    @DeleteMapping("/room/exit/{roomId}")
    public ResponseEntity<ResponseDto> exitChatRoom(@PathVariable String roomId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return chatRoomService.exitChatRoom(roomId, userDetailsImpl.getUser());
    }
}
