package com.example.hanghae_market.dto;


import com.example.hanghae_market.entity.ImagePath;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImagePathResponseDto {

    private String imagePath;

    public ImagePathResponseDto(ImagePath imagePath) {
        this.imagePath = imagePath.getImagePath();
    }
}
