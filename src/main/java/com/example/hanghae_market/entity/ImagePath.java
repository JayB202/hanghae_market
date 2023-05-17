package com.example.hanghae_market.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ImagePath {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    private String imagePath;

    @Column
    private String originalFilename;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public ImagePath(String originalFilename ,String imagePath, Post post) {
        this.originalFilename = originalFilename;
        this.imagePath = imagePath;
        this.post = post;
    }
}
