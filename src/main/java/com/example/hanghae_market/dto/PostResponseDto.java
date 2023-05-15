package com.example.hanghae_market.dto;

import com.example.hanghae_market.entity.Post;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@NoArgsConstructor
public class PostResponseDto {

    private MultipartFile image;
    private Long postId;
    private String postTitle;
    private String postContent;
    private int postPrice;
    private int interestCount;
    private String tradeLocation;
    private int tradeState;
    private String userId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.postPrice = post.getPostPrice();
        this.interestCount = post.getInterests().size();
        this.tradeLocation = post.getUser().getLocation();
        this.tradeState = post.getTradeState();
        this.userId = post.getUser().getUserId();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
