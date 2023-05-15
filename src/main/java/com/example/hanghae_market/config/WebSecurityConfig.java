package com.example.hanghae_market.config;

import com.example.hanghae_market.jwt.JwtAuthFilter;
import com.example.hanghae_market.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final JwtAuthFilter jwtAuthFilter;
    // swagger 적용시
    private static final String[] PERMIT_URL_ARRAY = {
    /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
    /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2 console 사용 및 resource 접근 허용 설정
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console()) // H2 사용시
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().cors().and().httpBasic().disable();
        // Session 방식 대신 JWT 방식을 사용
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.authorizeHttpRequests()
                .requestMatchers("/user/**").permitAll() // user 접근 승인
                .requestMatchers(HttpMethod.GET, "/post/**").permitAll()
                .requestMatchers(PERMIT_URL_ARRAY).permitAll() // swagger
                .anyRequest().authenticated().and().addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        // http.exceptionHandling().accessDeniedPage("/auth/forbidden");

        // 이 설정을 해주지 않으면 밑의 corsConfigurationSource가 적용되지 않습니다.
        httpSecurity.cors();
//                .and()
//                .csrf().disable()
//                .httpBasic().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeHttpRequests()
//                .requestMatchers("/auth/**").permitAll()
//                .requestMatchers("/post").permitAll()
//                .requestMatchers("/post/search").permitAll()
//                .requestMatchers(PERMIT_URL_ARRAY).permitAll()
//                .anyRequest().authenticated()
//                .and().addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }

}
