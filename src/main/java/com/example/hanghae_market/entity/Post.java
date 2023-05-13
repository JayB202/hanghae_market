package com.example.hanghae_market.entity;

import com.example.hanghae_market.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@Column
    //private String iamge;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String postContent;

    @Column
    @ElementCollection
    private List<String> tradeLocation;

    @Column
    private Date createdAt;

    @Column
    private Date modifiedAt;

    @Column
    private int tradeState;

    @ManyToOne
    private User user;


    public Post(PostRequestDto postRequestDto, User user) {
        this.postTitle = postRequestDto.getPostTitle();
        this.postContent = postRequestDto.getPostContent();
        this.tradeLocation = postRequestDto.getTradeLocationRequestDtoList();
        this.createdAt = getCreatedAt();
        this.modifiedAt = getModifiedAt();
        this.tradeState = 0;
        this.user = user;
    }
}
