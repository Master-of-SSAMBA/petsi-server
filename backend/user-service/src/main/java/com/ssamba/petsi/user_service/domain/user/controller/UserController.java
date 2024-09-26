package com.ssamba.petsi.user_service.domain.user.controller;

import com.ssamba.petsi.user_service.domain.user.service.UserService;
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

}
