package com.example.hanghae_market.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class PostRequestDto {
    private MultipartFile image;
    private String postTitle;
    private String postContent;
    private int postPrice;
}
