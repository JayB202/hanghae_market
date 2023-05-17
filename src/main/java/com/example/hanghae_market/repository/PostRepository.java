
package com.example.hanghae_market.repository;

import com.example.hanghae_market.entity.Post;
import com.example.hanghae_market.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();

    @Query("SELECT p, COUNT(i) as interestsCount FROM Post p LEFT JOIN p.interests i GROUP BY p ORDER BY interestsCount DESC")
    List<Post> findAllByOrderByInterestsCountDesc();

    List<Post> findAllByPostTitleContaining(String keyword);

    @Query("SELECT p FROM Post p WHERE p.user = :user AND EXISTS (SELECT i FROM Interest i WHERE i.post = p AND i.interest_status = :interestStatus)")
    List<Post> findPostsByUserAndInterestStatus(@Param("user") User user, @Param("interestStatus") Boolean interestStatus);

    List<Post> findAllByUserOrderByModifiedAtDesc(User user);

    Optional<Post> findByPostId(Long postId);

}
