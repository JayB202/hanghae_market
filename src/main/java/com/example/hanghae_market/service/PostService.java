package com.example.hanghae_market.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.hanghae_market.dto.PostRequestDto;
import com.example.hanghae_market.dto.PostResponseDto;
import com.example.hanghae_market.dto.ResponseDto;
import com.example.hanghae_market.entity.ImagePath;
import com.example.hanghae_market.entity.Interest;
import com.example.hanghae_market.entity.Post;
import com.example.hanghae_market.entity.User;
import com.example.hanghae_market.repository.ImagePathRepository;
import com.example.hanghae_market.repository.InterestRepository;
import com.example.hanghae_market.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final InterestRepository interestRepository;
    private final AmazonS3Client amazonS3Client;
    private final ImagePathRepository imagePathRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    @Transactional // 물품 등록
    public ResponseDto addPost(List<MultipartFile> multipartFileList, PostRequestDto postRequestDto, User user) {
        Post post = new Post(postRequestDto, user);
        List<ImagePath> imagePathList = new ArrayList<>();
        for (MultipartFile image : multipartFileList) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(image.getContentType());
            objectMetadata.setContentLength(image.getSize());

            String originalFilename = image.getOriginalFilename();
            int index = originalFilename.lastIndexOf(".");
            String ext = originalFilename.substring(index + 1);

            String storeFileName = UUID.randomUUID() + "." + ext;
            String key = "market/" + storeFileName;

            try (InputStream inputStream = image.getInputStream()) {
                amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();
            ImagePath imagePath = new ImagePath(originalFilename, storeFileUrl, post);
            imagePathRepository.saveAndFlush(imagePath);
            imagePathList.add(imagePath);
        }
        postRepository.saveAndFlush(post);
        return ResponseDto.setSuccess("물품 등록 완료");
    }

    @Transactional // 물품 수정
    public ResponseDto editPost(Long id, PostRequestDto postRequestDto, User user) {
        Post post = postValidation(id);
        if (post.getUser().getUserId().equals(user.getUserId())){
            post.edit(postRequestDto);
        } else throw new IllegalArgumentException("권한이 없습니다");
        return ResponseDto.setSuccess("물품이 수정되었습니다");
    }

    @Transactional // 거래상태 수정
    public ResponseDto editTrade(Long id, int tradeState, User user){
        Post post = postValidation(id);
        if (post.getUser().getUserId().equals(user.getUserId())){
            post.editTd(tradeState);
        } else throw new IllegalArgumentException("권한이 없습니다");
        return ResponseDto.setSuccess("거래 상태 변경 완료");
    }

    @Transactional // 삭제
    public ResponseDto deleteTrade(Long id, User user){
        Post post = postValidation(id);
        if (post.getUser().getUserId().equals(user.getUserId())){
            postRepository.deleteById(id);
        } else throw new IllegalArgumentException("권한이 없습니다");
        return ResponseDto.setSuccess("data delete");
    }

    @Transactional // 끌올
    public ResponseDto upPost(Long id, User user){
        Post post = postValidation(id);
        if (post.getUser().getUserId().equals(user.getUserId())){
            post.setModifiedAt(LocalDateTime.now());
        } else throw new IllegalArgumentException("권한이 없습니다");
        return ResponseDto.setSuccess("끌올 성공!");
    }

    @Transactional //관심상품 하트 누르기
    public ResponseDto postInterest(Long id, User user) {
        Post post = postValidation(id);
        Optional<Interest> interest = interestRepository.findByUserAndPost(user, post);
        if (interest.isEmpty()) {
            interestRepository.saveAndFlush(new Interest(true, post, user));
        } else {
            Boolean like = interest.get().getInterest_status();
            interest.get().setInterest_status(!like);
        }
        return ResponseDto.setSuccess("request success");
    }

    @Transactional // 전체조회
    public ResponseDto<List<PostResponseDto>> findAllPost() {
        List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post : postList) {
            postResponseDtoList.add(new PostResponseDto(post));
        }
        return ResponseDto.setSuccess("all data response", postResponseDtoList);
    }

    @Transactional // 상세조회
    public ResponseDto<PostResponseDto> findPostId(Long id){
        Post post = postValidation(id);
        return ResponseDto.setSuccess("data response", new PostResponseDto(post));
    }

    @Transactional // 추천상품 조회
    public ResponseDto<List<PostResponseDto>> findLikePost(){
        List<Post> likepost = postRepository.findByOrderByInterestsDesc();
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post : likepost) {
            postResponseDtoList.add(new PostResponseDto(post));
        }
        return ResponseDto.setSuccess("interest data response", postResponseDtoList);
    }

    @Transactional // 검색상품 조회
    public ResponseDto<List<PostResponseDto>> findSearch(String keyword){
        List<Post> searchPost = postRepository.findByPostTitleContaining(keyword);
        if (searchPost.isEmpty()){
            ResponseDto.setBadRequest("검색 결과가 없습니다");
        } else {
            List<PostResponseDto> postResponseDtoList = new ArrayList<>();
            for (Post post : searchPost) {
                postResponseDtoList.add(new PostResponseDto(post));
                return ResponseDto.setSuccess("searched data response", postResponseDtoList);
            }
        }
        return null;
    }

    private Post postValidation(Long id){
        return postRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("존재하지 않는 물품입니다")
        );
    }
}
