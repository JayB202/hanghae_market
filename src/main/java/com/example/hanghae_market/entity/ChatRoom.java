package com.example.hanghae_market.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom extends  Timestamped{
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long Id;
    @Column(name = "chatRoomId", nullable = false, unique = true)
    private String chatRoomId;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE)
    List<Chat> chatMessages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="postId")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    private User seller;

    private boolean isSellerExited;
    private int sellerChatCount;

    @ManyToOne(fetch = FetchType.EAGER)
    private User buyer;

    private boolean isBuyerExited;
    private int buyerChatCount;

    public static ChatRoom of(Post post, User user) {
        return ChatRoom.builder()
                .chatRoomId(UUID.randomUUID().toString())
                .post(post)
                .seller(post.getUser())
                .buyer(user)
                .build();
    }

    public void setSellerExited(boolean sellerExited){
        this.isSellerExited = sellerExited;
    }

    public void setBuyerExited(boolean buyerExited){
        this.isBuyerExited = buyerExited;
    }

    public void setSellerChatCount(int count) {
        this.sellerChatCount = count;
    }

    public void setBuyerChatCount(int count) {
        this.buyerChatCount = count;
    }

    public void initSellerChatCount() {
        this.sellerChatCount = 0;
    }

    public void initBuyerChatCount() {
        this.buyerChatCount = 0;
    }

    public boolean isSeller(User user) {
        return getSeller().getUserId().equals(user.getUserId());
    }






}
