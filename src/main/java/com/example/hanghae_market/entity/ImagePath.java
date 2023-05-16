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
    private Long imagePathId;

    @Column
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public ImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
