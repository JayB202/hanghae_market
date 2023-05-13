package com.example.hanghae_market.dto;

import com.example.hanghae_market.entity.Post;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostResponseDto {

    private Long postId;
    private String postTitle;
    private String postContent;
    private int postPrice;
    private int interestCount;
    private String tradeLocation;
    private int tradeState;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.postPrice = post.getPostPrice();
        this.interestCount = post.getInterests().size();
        this.tradeLocation = tradeLocation;
        this.tradeState = post.getTradeState();
    }
}
