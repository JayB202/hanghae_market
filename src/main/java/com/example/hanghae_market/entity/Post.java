package com.example.hanghae_market.entity;

import com.example.hanghae_market.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Entity
@Getter

@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    @Column
    private String image;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String postContent;

    @Column(nullable = false)
    private int postPrice;

    @Column(nullable = false)
    private int tradeState;


    @Column
    private String tradeLocation;

    @Column
    private String specificLocation;

    @Column
    private Boolean isShared;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    List<Interest> interests;


    public Post(PostRequestDto postRequestDto, User user) {
//        this.image = postRequestDto.getImage();

        this.postTitle = postRequestDto.getPostTitle();
        this.postContent = postRequestDto.getPostContent();
        this.postPrice = postRequestDto.getPostPrice();
        this.tradeState = 0;
        this.tradeLocation = postRequestDto.getTradeLocation();
        this.specificLocation = postRequestDto.getSpecificLocation();
        this.isShared = postRequestDto.getIsShared();
        this.user = user;
    }

    public void edit(MultipartFile image, PostRequestDto postRequestDto) {
        this.postTitle = postRequestDto.getPostTitle();
        this.postContent = postRequestDto.getPostContent();
    }

    public void editTd(int tradeState) {
        this.tradeState = tradeState;
    }

}
