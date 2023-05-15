package com.example.hanghae_market.repository;

import com.example.hanghae_market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM TB_USER u WHERE u.userId = :userId")
    Optional<User> findByUserId(@Param("userId") String userId);
}
