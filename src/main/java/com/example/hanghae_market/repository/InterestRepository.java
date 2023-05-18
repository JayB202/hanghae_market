
package com.example.hanghae_market.repository;

import com.example.hanghae_market.entity.Interest;
import com.example.hanghae_market.entity.Post;
import com.example.hanghae_market.entity.RefreshToken;
import com.example.hanghae_market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaAuditing
public interface InterestRepository extends JpaRepository<Interest, Long> {
    Optional<Interest> findByUserAndPost(User user, Post post);

//    List<Interest> findAllByUserAndInterestStatus(User user, Boolean interestStatus);

    @Query("SELECT i FROM Interest i WHERE i.interest_status = true AND i.user = :user")
    List<Interest> findAllByUser(@Param("user") User user);

}
