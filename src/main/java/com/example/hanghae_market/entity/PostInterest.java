package com.example.hanghae_market.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class PostInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postInterestId;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    @ColumnDefault("false")
    private boolean postInterests;

    public PostInterest(Long postInterestId, User user, boolean postInterests){
        this.postInterestId =postInterestId;
        this.user = user;
        this.postInterests = postInterests;
    }

}
