package com.example.hanghae_market.controller;

import com.example.hanghae_market.dto.InterestResponseDto;
import com.example.hanghae_market.security.UserDetailsImpl;
import com.example.hanghae_market.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InterestController {
    private final InterestService interestService;

    @PostMapping("/interest/{postId}")
    public InterestResponseDto postInterestService(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return interestService.postInterestService(postId, userDetailsImpl.getUser());
    }

}