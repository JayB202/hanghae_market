package com.example.hanghae_market.repository;

import com.example.hanghae_market.entity.Post;
import com.example.hanghae_market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByPostId(Long postId);

    List<Post> findAllByOrderByModifiedAtDesc();

//    @Query("SELECT p FROM Post p WHERE p.interests=TRUE")
//    List<Post> findByOrderByInterestsDesc();
//
//    List<Post> findByPostTitleContaining(String keyword);
//
//    @Query("SELECT p FROM Post p WHERE p.interests=TRUE")
//    List<Post> findByUser(User user);

    List<Post> findByUserOrderByModifiedAtDesc(User user);

}
