package com.example.hanghae_market.repository;

import com.example.hanghae_market.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();

    @Query("SELECT * FROM Post p WHERE p.interests=TRUE")
    List<Post> findByOrderByInterestsDesc();

}
