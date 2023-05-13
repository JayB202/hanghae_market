package com.example.hanghae_market.controller;

import com.example.hanghae_market.dto.PostRequestDto;
import com.example.hanghae_market.dto.ResponseDto;
import com.example.hanghae_market.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {

    private final PostService postService;

    @PostMapping("/add")
    public ResponseDto addPost(@RequestParam("image")MultipartFile image, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.addPost(image, postRequestDto, userDetails.getUser());
    }

    @PutMapping("/{postid}")
    public ResponseDto editPost(@PathVariable("postid") Long id, @RequestParam("image") MultipartFile image, @RequestParam PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.editPost(id, image, postRequestDto, userDetails.getUser());
    }
}
