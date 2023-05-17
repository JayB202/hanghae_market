package com.example.hanghae_market.controller;

import com.example.hanghae_market.dto.UserRequestDto;
import com.example.hanghae_market.dto.UserResponseDto;
import com.example.hanghae_market.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public UserResponseDto signup(@Valid @RequestBody UserRequestDto userRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                sb.append(fieldError.getDefaultMessage());
            }
            System.out.println(sb);
            return new UserResponseDto(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        return userService.signup(userRequestDto);

    }

    @PostMapping("/idCheck")
    public UserResponseDto idCheck(@RequestBody Map<String, String> userId) {
        return userService.idCheck(userId);
    }

    @PostMapping("/login")
    public UserResponseDto login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
        return userService.login(userRequestDto, response);
    }

    @DeleteMapping("/logout")
    public UserResponseDto logout(@RequestBody UserRequestDto userRequestDto) {
        return userService.logout(userRequestDto);
    }

    @GetMapping("/phoneCheck")
    public String sendSMS(@RequestParam("phone") String userPhoneNumber, HttpServletRequest request) throws UnsupportedEncodingException { // 휴대폰 문자보내기
        int randomNumber = (int)((Math.random()* (9999 - 1000 + 1)) + 1000);//난수 생성

        userService.certifiedPhoneNumber(userPhoneNumber, randomNumber);

        return Integer.toString(randomNumber);
    }
}
