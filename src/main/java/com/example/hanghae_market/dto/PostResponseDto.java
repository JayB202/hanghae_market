package com.example.hanghae_market.dto;

import com.example.hanghae_market.entity.ImagePath;
import com.example.hanghae_market.entity.Interest;
import com.example.hanghae_market.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {

    private List<ImagePathResponseDto> imagePathList;
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
    private String specificLocation;
    private Boolean isShared;

    public PostResponseDto(Post post) {
        for (ImagePath imagePath: post.getImagePathList()) {
            ImagePathResponseDto imagePathResponseDto = new ImagePathResponseDto(imagePath);
            this.imagePathList.add(imagePathResponseDto);
        }
        this.postId = post.getPostId();
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.postPrice = post.getPostPrice();
        this.interestCount = (int) post.getInterests().stream().filter(Interest::getInterest_status).count();
        this.tradeLocation = post.getUser().getLocation();
        this.tradeState = post.getTradeState();
        this.userId = post.getTradeLocation();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.specificLocation = post.getSpecificLocation();
        this.isShared = post.getIsShared();


    }
}
