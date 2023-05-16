package com.example.hanghae_market.jwt;

import com.example.hanghae_market.dto.UserResponseDto;
import com.example.hanghae_market.entity.User;
import com.example.hanghae_market.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.hanghae_market.jwt.JwtUtil.ACCESS_KEY;
import static com.example.hanghae_market.jwt.JwtUtil.REFRESH_KEY;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    //필터체인 : 필터가 체인형식으로 묶여있어 필터끼리 이동이 가능
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String access_token = jwtUtil.resolveToken(request, ACCESS_KEY);
        String refresh_token = jwtUtil.resolveToken(request, REFRESH_KEY);

        // accesstoken 유무 검사
        if(access_token != null) {
            // token 유효성 검사
            if(jwtUtil.validateToken(access_token)) {
                // accessToken에 이상 유무 확인 ( false => 정상 )
                setAuthentication(jwtUtil.getUserInfoFromToken(access_token));
            }

            // accessToken 만료 and refreshToken 존재할시 => refreshToken 검증( 유효성, DB에 존재 여부 확인 )
            else if (refresh_token != null && jwtUtil.refreshTokenValidation(refresh_token)) {
                // 리프레시 토큰으로 username, Member DB에서 username을 가진 member 가져오기
                String userId = jwtUtil.getUserInfoFromToken(refresh_token);
                User user = userRepository.findByUserId(userId).get();
                // 새로운 액세스 토큰 발급
                String newAccessToken = jwtUtil.createToken(userId, user.getRole(), "Access");
                // header에 액세스 토큰 추가
                jwtUtil.setHeaderAccessToken(response, newAccessToken);
                // Security context에 인증 정보 넣기
                setAuthentication(userId);
            } else if (refresh_token == null) {
                jwtExceptionHandler(response, "AccessToken has Expired. Please send your RefreshToken together.", HttpStatus.BAD_REQUEST.value());
            } else {
                // (토큰 만료 && 리프레시 토큰 만료) || 리프레시 토큰이 DB와 비교했을 때 같지 않다면
                jwtExceptionHandler(response, "RefreshToken Expired", HttpStatus.BAD_REQUEST.value());
                return;
            }

        }
        filterChain.doFilter(request, response);
    }

    private void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new UserResponseDto(msg, HttpStatus.UNAUTHORIZED));
            response.getWriter().write(json);
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
        // 인증객체가 다 만들어져서 SecurityContextHolder 안의 SecurityContext 에 인증객체가 들어있으면
        // 다음 Filter로 이동했을 때 이 요청은 인증이 되었다는 것을 Security 쪽에서 인지를 하고
        // Controller 까지 요청이 넘어간다.
    }

    

}
