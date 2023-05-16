package com.example.hanghae_market.repository;

import com.example.hanghae_market.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
