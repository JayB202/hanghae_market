
package com.example.hanghae_market.service;

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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final PostRepository postRepository;
    private final InterestRepository interestRepository;

    @Transactional // 마이페이지 관심상품 조회
    public ResponseDto<List<PostResponseDto>> interestMypage(User user){
        List<Interest> myInterestList = interestRepository.findAllByUser(user);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for(Interest interest  : myInterestList){
            postResponseDtoList.add(new PostResponseDto(interest.getPost()));
        }
        return ResponseDto.setSuccess("My interest Post response", postResponseDtoList);
    }

    @Transactional // 마이페이지 판매목록 조회
    public ResponseDto<List<PostResponseDto>> myPost(User user){
        List<Post> postList = postRepository.findAllByUserOrderByModifiedAtDesc(user);
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post : postList) {
            postResponseDtoList.add(new PostResponseDto(post));
        }
        return ResponseDto.setSuccess("My Post response", postResponseDtoList);
    }
}

