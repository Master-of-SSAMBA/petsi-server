package com.ssamba.petsi.user_service.domain.user.controller;

import com.ssamba.petsi.user_service.domain.user.dto.request.CheckEmailRequestDto;
import com.ssamba.petsi.user_service.domain.user.dto.request.SignupRequestDto;
import com.ssamba.petsi.user_service.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "UserController", description = "유저 컨트롤러")
public class UserController {

    private final UserService userservice;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        userservice.signup(signupRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("/check-email")
    @Operation(summary = "이메일 확인")
    public ResponseEntity<?> checkDuplicateEmail(@Valid @RequestBody CheckEmailRequestDto checkEmailRequestDto) {
        userservice.isValidEmail(checkEmailRequestDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
