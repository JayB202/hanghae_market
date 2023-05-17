//package com.example.hanghae_market.service;
//
//import com.example.hanghae_market.dto.InterestResponseDto;
//import com.example.hanghae_market.entity.Post;
//import com.example.hanghae_market.entity.PostInterest;
//import com.example.hanghae_market.entity.User;
//import com.example.hanghae_market.repository.PostInterestRepository;
//import com.example.hanghae_market.repository.PostRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class InterestService {
//    private final PostRepository postRepository;
//    private final PostInterestRepository postInterestRepository;
//
//    @Transactional
//    public InterestResponseDto postInterestService(Long postId, User user){
//        Post post = postCheck(postId);
//
//        Optional<PostInterest> interestCheck = postInterestRepository.findByPostInterestsIsEndingWithAndPostInterestId(user.getUserId(), postId);
//
//        if (interestCheck.isPresent()) {
//            PostInterest postInterest = postInterestCheck(user.getUserId(), postId);
//            boolean likeChk = postInterest.isPostInterests();
//            postInterest.setPostInterests(!likeChk);
//            postInterestRepository.save(postInterest);
//        } else {
//            PostInterest postInterest = new PostInterest(post.getPostId(), user,  true);
//            postInterestRepository.save(postInterest);
//        }
//        long interests = postInterestRepository.getPostInterestsCount(postId);
//        post.setPostInterests(interests);
//        postRepository.save(post);
//
//        return new InterestResponseDto("좋아요", HttpStatus.OK);
//    }
//
//    private PostInterest postInterestCheck(String userId, Long postId) {
//        return postInterestRepository.findByPostInterestsIsEndingWithAndPostInterestId(userId, postId).orElseThrow(
//                ()-> new IllegalArgumentException("실패")
//        );
//    }
//
//    @Transactional
//    public Post postCheck(Long postId){
//        return postRepository.findByPostId(postId).orElseThrow(
//                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다")
//        );
//    }
//}
