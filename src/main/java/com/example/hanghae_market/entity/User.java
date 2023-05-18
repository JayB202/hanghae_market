
package com.example.hanghae_market.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import java.util.List;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "TB_USER")
public class User {
    @Id
    @Column(name = "userId", nullable = false, unique = true)
//    @Pattern(regexp = "^(?=.*[a-zA-Z0-9]).{5,12}$",
//            message = "아이디는 5-12자 문자, 숫자로 입력해주세요.")
    private String userId;

    @Column(nullable = false)
//    @Pattern(regexp = "^(?=.*[a-zA-Z0-9]).{5,12}$",  //^[a-zA-Z0-9]{5,12}$
//            message = "비밀번호는 5-12자 문자, 숫자로 입력해주세요.")
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Interest> interests;


    public User(String userId, String password, String email, String location, String phone, UserRole role) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.location = location;
        this.phone = phone;
        this.role = role;
    }
}

