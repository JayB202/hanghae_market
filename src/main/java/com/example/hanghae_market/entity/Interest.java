package com.example.hanghae_market.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    private Boolean interest_status;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    private User user;

    public Interest(boolean interest, Post post, User user) {
        this.interest_status = interest;
        this.post = post;
        this.user = user;
    }

    public void setInterest_status(boolean interest_status) {
        this.interest_status = interest_status;
    }
}
