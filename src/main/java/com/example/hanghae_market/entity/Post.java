
package com.example.hanghae_market.entity;

import com.example.hanghae_market.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    @Column(nullable = true)
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

//    @ColumnDefault("0")
//    private Long postInterests;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    List<Interest> interests;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    List<ImagePath> imagePathList;


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
//        this.postInterests = 0L;
    }

    public void edit(PostRequestDto postRequestDto) {
        this.postTitle = postRequestDto.getPostTitle();
        this.postContent = postRequestDto.getPostContent();
    }

    public void editTd(int tradeState) {
        this.tradeState = tradeState;
    }


}