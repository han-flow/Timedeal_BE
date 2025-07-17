package com.timedeal.domain.member.controller;

import com.timedeal.domain.member.dto.EmailAuthRequest;
import com.timedeal.domain.member.dto.EmailAuthVerifyRequest;
import com.timedeal.domain.member.dto.SignupDTO;
import com.timedeal.domain.member.service.AuthService;
import com.timedeal.domain.member.service.email.EmailService;
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
    private final EmailService emailService;

    @PostMapping("/signup")
    public BaseResponse<Empty> signup(@RequestBody @Valid SignupDTO signupDTO) {
        authService.signup(signupDTO);
        return BaseResponse.success();
    } // 자체 이메일 회원가입


    // 이메일 인증 코드 전송
    @PostMapping("/send-email-code")
    public BaseResponse<Empty> sendEmailVerificationCode(@RequestBody @Valid EmailAuthRequest emailAuthRequest) {
        emailService.sendEmailVerificationCode(emailAuthRequest.getEmail());
        return BaseResponse.success();
    }

    // 이메일 인증 코드 검증
    @PostMapping("/verify-email")
    public BaseResponse<Empty> verifyEmailCode(@RequestBody @Valid EmailAuthVerifyRequest emailAuthVerifyRequest) {
        emailService.verifyEmailCode(emailAuthVerifyRequest.getEmail(), emailAuthVerifyRequest.getCode());
        return BaseResponse.success();
    }
}
