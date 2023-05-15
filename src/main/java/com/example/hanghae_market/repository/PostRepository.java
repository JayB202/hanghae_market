package com.example.hanghae_market.repository;

import com.example.hanghae_market.entity.Post;
import com.example.hanghae_market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();

    @Query("SELECT p FROM Post p WHERE p.interests=TRUE")
    List<Post> findByOrderByInterestsDesc();

    List<Post> findByPostTitleContaining(String keyword);

    @Query("SELECT p FROM Post p WHERE p.interests=TRUE")
    List<Post> findByUser(User user);

    List<Post> findByUserOrderByModifiedAtDesc(User user);
}
