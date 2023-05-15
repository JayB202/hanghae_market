package com.example.hanghae_market.repository;

import com.example.hanghae_market.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository  extends JpaRepository<ChatRoom, String> {

    Optional<ChatRoom> findByChatRoomId(String chatRoomId);

//    List<ChatRoom> findAllBySellerIdOrBuyerIdOrderByModifiedAtDesc(String sellerId, String buyerId);

    @Query("SELECT c FROM ChatRoom c WHERE c.buyer = :buyerId or c.seller = :sellerId")
    List<ChatRoom> findAllByBuyerIdOrSellerID(@Param("buyerId") String buyerId, @Param("sellerId") String sellerId);

//    Optional<ChatRoom> findChatRoomByPostIdAndBuyerId(Long postId, String Id);
//
    @Query("SELECT c FROM ChatRoom c WHERE c.post = :postId and c.buyer = :buyerId")
    Optional<ChatRoom> findByPostIdAndBuyerId(@Param("postId") Long postId, @Param("buyerId") String buyerId);
}
