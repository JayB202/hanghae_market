package com.example.hanghae_market.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data // getter, setter,toString , EqualsAndHashCode , RequiredArgsConstructor
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만들기
@NoArgsConstructor // final이나 @NonNull인 필드 값만 파라미터로 받는 생성자 만듦
public class UserRequestDto {

    private String userId;

    private String password;

    // erd에 존재하지만 명세서에 존재하지않아서 주석처리
    // private String username;

//    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    private String phone;

    private String location;

    private String userRole = "User";
    private String adminToken = "";


}
