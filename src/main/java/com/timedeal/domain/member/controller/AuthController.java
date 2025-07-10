package com.timedeal.domain.member.controller;

import com.timedeal.domain.member.dto.SignupDTO;
import com.timedeal.domain.member.service.AuthService;
import com.timedeal.global.dto.BaseResponse;
import com.timedeal.global.dto.Empty;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public BaseResponse<Empty> signup(@RequestBody @Valid SignupDTO signupDTO) {
        authService.signup(signupDTO);
        return BaseResponse.success();
    } // 자체 이메일 회원가입


}
