package com.example.hanghae_market.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @Column(name = "postId", nullable = false)
    private Long postId;

    @Column
    private String postTitle;

    @ManyToOne
    @JoinTable(name="TB_USER")
    private User user;
}
