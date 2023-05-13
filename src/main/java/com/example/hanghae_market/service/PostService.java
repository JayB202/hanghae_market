package com.example.hanghae_market.service;

import com.example.hanghae_market.dto.PostRequestDto;
import com.example.hanghae_market.dto.ResponseDto;
import com.example.hanghae_market.entity.Post;
import com.example.hanghae_market.entity.User;
import com.example.hanghae_market.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public ResponseDto addPost(MultipartFile image, PostRequestDto postRequestDto, User user) {
        Post post = new Post(postRequestDto, user);
        postRepository.saveAndFlush(post);
        return ResponseDto.setSuccess("물품 등록 완료");
    }

    @Transactional
    public ResponseDto editPost()
}
