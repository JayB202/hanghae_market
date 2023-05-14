package com.example.hanghae_market.service;

import com.example.hanghae_market.dto.PostRequestDto;
import com.example.hanghae_market.dto.PostResponseDto;
import com.example.hanghae_market.dto.ResponseDto;
import com.example.hanghae_market.entity.Interest;
import com.example.hanghae_market.entity.Post;
import com.example.hanghae_market.entity.User;
import com.example.hanghae_market.repository.InterestRepository;
import com.example.hanghae_market.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final InterestRepository interestRepository;

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

    @Transactional
    public ResponseDto editTrade(Long id, int tradeState, User user){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 상품입니다")
        );
        if (post.getUser().getuserId().equals(user.getuserId())){
            post.editTd(tradeState);
        } else throw new IllegalArgumentException("권한이 없습니다");
        return ResponseDto.setSuccess("거래 상태 변경 완료");
    }

    @Transactional
    public ResponseDto upPost(Long id, User user){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 상품입니다")
        );
        if (post.getUser().getuserId().equals(user.getuserId())){
            post.setModifiedAt(LocalDateTime.now());
        } else throw new IllegalArgumentException("권한이 없습니다");
        return ResponseDto.setSuccess("끌올 성공!");
    }

    @Transactional
    public ResponseDto postInterest(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 상품입니다")
        );
        Optional<Interest> interest = interestRepository.findByUseraAndPost(user, post);
        if (interest.isEmpty()) {
            interestRepository.saveAndFlush(new Interest(true, post, user));
        } else {
            Boolean like = interest.get().getInterest_status();
            interest.get().setInterest_status(!like);
        }
        return ResponseDto.setSuccess("request success");
    }

    @Transactional
    public ResponseDto<List<PostResponseDto>> findAllPost() {
        List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post : postList) {
            postResponseDtoList.add(new PostResponseDto(post));
        }
        return ResponseDto.setSuccess("all data response", postResponseDtoList);
    }

    @Transactional
    public ResponseDto<PostResponseDto> findPostId(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 물품입니다")
        );
        return ResponseDto.setSuccess("data response", new PostResponseDto(post));
    }


}
