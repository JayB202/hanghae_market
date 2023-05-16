package com.example.hanghae_market.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hanghae_market.entity.PostInterest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostInterestRepository extends JpaRepository<PostInterest, Long> {
    Optional<PostInterest> findByPostInterestsIsEndingWithAndPostInterestId(String userId, Long postId);

    @Query("SELECT count(p) FROM PostInterest p WHERE p.postInterestId = :postId AND p.postInterests= true")
    Long getPostInterestsCount(@Param("postId") Long postId);

}
