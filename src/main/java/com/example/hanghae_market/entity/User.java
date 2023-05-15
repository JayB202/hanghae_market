package com.example.hanghae_market.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@NoArgsConstructor
@Getter
@Entity(name = "TB_USER")
public class User {
    @Id
    @Column(name = "userId", nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

//    @Column(nullable = false)
//    private String username;
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public User(String userId, String password, String email, String location, String phone, UserRole role) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.location = location;
        this.phone = phone;
        this.role = role;
    }
}
