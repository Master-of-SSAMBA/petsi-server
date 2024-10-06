package com.ssamba.petsi.user_service.domain.user.controller;

import com.ssamba.petsi.user_service.domain.user.dto.request.CheckEmailRequestDto;
import com.ssamba.petsi.user_service.domain.user.dto.request.PatchNicknameDto;
import com.ssamba.petsi.user_service.domain.user.dto.request.SignupRequestDto;
import com.ssamba.petsi.user_service.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/email-check")
    @Operation(summary = "이메일 확인")
    public ResponseEntity<?> checkDuplicateEmail(@Valid @RequestBody CheckEmailRequestDto checkEmailRequestDto) {
        userservice.isValidEmail(checkEmailRequestDto.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PatchMapping("/nickname")
    @Operation(summary = "닉네임 변경")
    public ResponseEntity<?> changeNickname(@RequestHeader("X-User-Id") Long userId,
                                            @Valid @RequestBody PatchNicknameDto patchNicknameDto) {
        userservice.changeNickname(userId, patchNicknameDto);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PatchMapping("/image")
    @Operation(summary = "프로필 사진 변경")
    public ResponseEntity<?> changeImg(@RequestHeader("X-User-Id") Long userId,
                                       @RequestPart(value = "profile_image", required = true) MultipartFile img) {
        userservice.changeImg(userId, img);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
