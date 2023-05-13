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
    public ResponseDto editPost(Long id, MultipartFile image, PostRequestDto postRequestDto, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 상품입니다")
        );
        if (post.getUser().getuserId().equals(user.getuserId())){
            post.edit(image, postRequestDto);
        } else throw new IllegalArgumentException("권한이 없습니다");
        return ResponseDto.setSuccess("물품이 수정되었습니다");
    }
}
