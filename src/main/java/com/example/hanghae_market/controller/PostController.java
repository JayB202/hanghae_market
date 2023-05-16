
package com.example.hanghae_market.controller;

import com.example.hanghae_market.dto.PostRequestDto;
import com.example.hanghae_market.dto.PostResponseDto;
import com.example.hanghae_market.dto.ResponseDto;
import com.example.hanghae_market.security.UserDetailsImpl;
import com.example.hanghae_market.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {

    private final PostService postService;

    @PostMapping("/add") public ResponseDto addPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.addPost(postRequestDto, userDetails.getUser());
    }

    @PutMapping("/{postid}")
    public ResponseDto editPost(@PathVariable("postid") Long postid, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.editPost(postid, postRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/{postid}/delete")
    public ResponseDto deletePost(@PathVariable("postid") Long postid, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.deleteTrade(postid, userDetails.getUser());
    }

    @PostMapping("/{postid}/tradestatus")
    public ResponseDto editTrade(@PathVariable("postid") Long postid, @RequestBody int tradeState, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.editTrade(postid, tradeState, userDetails.getUser());
    }

    @PutMapping("/up/{postid}")
    public ResponseDto upPost(@PathVariable("postid") Long postid, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.upPost(postid, userDetails.getUser());
    }

    @PostMapping("/{postid}/interest")
    public ResponseDto postInterest(@PathVariable("postid") Long postid, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.postInterest(postid, userDetails.getUser());
    }

    @GetMapping
    public ResponseDto<List<PostResponseDto>> findAllPost(){
        return postService.findAllPost();
    }

    @GetMapping("/{postid}")
    public ResponseDto<PostResponseDto> findPostId(@PathVariable("postid") Long postid){
        return postService.findPostId(postid);
    }

    @GetMapping("/recommend")
    public ResponseDto<List<PostResponseDto>> findLikePost(){
        return postService.findLikePost();
    }

    @GetMapping("/search")
    public ResponseDto<List<PostResponseDto>> findSearch(@RequestParam("keyword") String keyword) {
        return postService.findSearch(keyword);
    }
}


