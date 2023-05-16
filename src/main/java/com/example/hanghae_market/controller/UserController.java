package com.example.hanghae_market.controller;

import com.example.hanghae_market.dto.UserRequestDto;
import com.example.hanghae_market.dto.UserResponseDto;
import com.example.hanghae_market.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public UserResponseDto login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
        return userService.login(userRequestDto, response);
    }

    @DeleteMapping("/logout")
    public UserResponseDto logout(@RequestBody UserRequestDto userRequestDto) {
        return userService.logout(userRequestDto);
    }
}
