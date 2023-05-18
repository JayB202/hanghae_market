package com.example.hanghae_market.service;

import com.example.hanghae_market.dto.TokenDto;
import com.example.hanghae_market.dto.UserRequestDto;
import com.example.hanghae_market.dto.UserResponseDto;
import com.example.hanghae_market.entity.RefreshToken;
import com.example.hanghae_market.entity.User;
import com.example.hanghae_market.entity.UserRole;
import com.example.hanghae_market.jwt.JwtUtil;
import com.example.hanghae_market.repository.RefreshTokenRepository;
import com.example.hanghae_market.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.example.hanghae_market.jwt.JwtUtil.ACCESS_KEY;
import static com.example.hanghae_market.jwt.JwtUtil.REFRESH_KEY;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    // 관리자탭을 만들시 관리자 전용 토큰
    private static final String ADMIN_TOKEN = "FJdsofpdsjfdpsofjfsfFDSF";
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public UserResponseDto signup(UserRequestDto userRequestDto) {
        String userId = userRequestDto.getUserId();
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        String email = userRequestDto.getEmail();
        String phone = userRequestDto.getPhone();
        String location = userRequestDto.getLocation();
        String userRole = userRequestDto.getUserRole();

//        Optional<User> found = userRepository.findByUserId(userId);
//
//        if (found.isPresent()) {
//            return new UserResponseDto("아이디 중복", HttpStatus.BAD_REQUEST);
//        }

        UserRole role;
        // 관리자 토큰 확인( 일반사용자인지 관리자인지 )
        if (userRole.equals("admin")) {
            if (!ADMIN_TOKEN.equals(userRequestDto.getAdminToken())) {
                return new UserResponseDto("토큰값이 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
            }
            role =  UserRole.ADMIN;
        } else{
            role =  UserRole.USER;
        }
        User user = new User(userId, password, email, location, phone,  role);
        userRepository.save(user);
        return new UserResponseDto("회원 가입이 성공했습니다. 환영합니다.", HttpStatus.OK);
    }

    //중복 체크
    public UserResponseDto idCheck(Map<String, String> userId) {
        Optional<User> found = userRepository.findByUserId(String.valueOf(userId.get("userId")));

        if (found.isPresent()) {
            return new UserResponseDto("사용중인 아이디입니다.", HttpStatus.BAD_REQUEST);
        }
        return new UserResponseDto("사용 가능한 아이디입니다.", HttpStatus.OK);
    }

    @Transactional
    public UserResponseDto login(UserRequestDto userRequestDto, HttpServletResponse response) {

        String userId = userRequestDto.getUserId();
        String password = userRequestDto.getPassword();

            if(!userRepository.findByUserId(userId).isPresent()){
                return new UserResponseDto("존재하지 않는 유저입니다", HttpStatus.BAD_REQUEST);
            }
            User user = userRepository.findByUserId(userId).orElseThrow();

//          elseThrow(() ->  new IllegalArgumentException( "아이디가 존재하지않습니다.")); //return?

            if (!passwordEncoder.matches(password, user.getPassword())) {
                return new UserResponseDto("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
            }

            //userId 정보로 Token 생성
            TokenDto tokenDto = jwtUtil.createAllToken(userRequestDto.getUserId(), user.getRole());

            // RerfreshToken 확인
            Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserId(userRequestDto.getUserId());

            // Refresh 토큰이 있다면 재발급 후 업데이트
            if (refreshToken.isPresent()) {
                RefreshToken savedRefreshToken = refreshToken.get();
                RefreshToken updateToken = savedRefreshToken.updateToken(tokenDto.getRefreshToken().substring(7));
                refreshTokenRepository.save(updateToken);
            } else {
                RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken().substring(7), userId);
                refreshTokenRepository.save(newToken);
            }

            // reponse header에 token 추가
            setHeader(response, tokenDto, user);
            return new UserResponseDto("성공", HttpStatus.OK);
            //  response.addHeader(jwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUserId(), "USER"));
            //  return new UserResponseDto("로그인 성공", HttpStatus.OK);

    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto, User user) {
        response.addHeader(ACCESS_KEY, tokenDto.getAccessToken());
        response.addHeader(REFRESH_KEY, tokenDto.getRefreshToken());
        response.addHeader("ROLE_USER", user.getRole().getAuthority());
    }


    @Transactional
    public UserResponseDto logout(UserRequestDto requestDto) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("토큰이 존재하지않습니다."));

        refreshTokenRepository.delete(refreshToken);

        return new UserResponseDto("로그아웃 성공", HttpStatus.OK);
    }

    public void certifiedPhoneNumber(String userPhoneNumber, int randomNumber) {
        String api_key = "NCSWA6JF3ZIAZEZQ";
        String api_secret = "UCLVBF51YFVWUX63PFQKLBTUBBVNZWBU";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", userPhoneNumber);    // 수신전화번호
        params.put("from", "01040742070");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "[HangHaeMarket] 인증번호는" + "["+randomNumber+"]" + "입니다."); // 문자 내용 입력
        params.put("app_version", "app 1.2"); // application name and version

        try {
            JSONObject obj = coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

    }

}
