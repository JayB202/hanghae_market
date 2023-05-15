package com.example.hanghae_market.repository;

import com.example.hanghae_market.entity.Interest;
import com.example.hanghae_market.entity.Post;
import com.example.hanghae_market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    Optional<Interest> findByUserAndPost(User user, Post post);
}
