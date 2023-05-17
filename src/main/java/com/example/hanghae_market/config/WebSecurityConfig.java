
package com.example.hanghae_market.config;

import com.example.hanghae_market.jwt.JwtAuthFilter;
import com.example.hanghae_market.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;



@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig {

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
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf().disable().cors().and().httpBasic().disable();
//        // Session 방식 대신 JWT 방식을 사용
//        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        httpSecurity.authorizeHttpRequests()
//                .requestMatchers("/user/**").permitAll() // user 접근 승인
////                .requestMatchers(HttpMethod.GET, "/post").permitAll()
//                .requestMatchers(HttpMethod.GET,"/post/**").permitAll()
//                .requestMatchers(PERMIT_URL_ARRAY).permitAll() // swagger
//                .anyRequest().authenticated().and().addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//        // http.exceptionHandling().accessDeniedPage("/auth/forbidden");

        // 이 설정을 해주지 않으면 밑의 corsConfigurationSource가 적용되지 않습니다.
        httpSecurity.cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests()
                .requestMatchers("/user/**").permitAll()
//                .requestMatchers("/post").permitAll()
                .requestMatchers(HttpMethod.GET,"/post/**").permitAll()
//                .requestMatchers(PERMIT_URL_ARRAY).permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }
   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
       CorsConfiguration corsConfiguration = new CorsConfiguration();

       corsConfiguration.setAllowedOriginPatterns(List.of("*"));
       corsConfiguration.addAllowedOrigin("http://localhost:3000");
       corsConfiguration.addAllowedOrigin("http://hhmarket.s3-website.ap-northeast-2.amazonaws.com");
       corsConfiguration.addAllowedOrigin("http://hhmarket.s3-website.ap-northeast-2.amazonaws.com:3000");
       corsConfiguration.addAllowedOriginPattern("*");
       corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "DELETE", "PUT"));
       corsConfiguration.setAllowedHeaders(List.of("*"));
       corsConfiguration.setAllowCredentials(true);
       corsConfiguration.addExposedHeader("*");

       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       source.registerCorsConfiguration("/**", corsConfiguration);
       return source;
   }


}
