package com.example.hanghae_market.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    private boolean interest;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
