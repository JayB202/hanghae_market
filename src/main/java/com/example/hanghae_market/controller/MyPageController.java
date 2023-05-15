package com.example.hanghae_market.controller;

import com.example.hanghae_market.dto.PostResponseDto;
import com.example.hanghae_market.dto.ResponseDto;
import com.example.hanghae_market.security.UserDetailsImpl;
import com.example.hanghae_market.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MypageService mypageService;

    @GetMapping("/mypage/interest")
    public ResponseDto<List<PostResponseDto>> interestMypage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.interestMypage(userDetails.getUser());
    }

    @GetMapping("/mypage")
    public ResponseDto<List<PostResponseDto>> myPost(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return mypageService.myPost(userDetails.getUser());
    }
}
