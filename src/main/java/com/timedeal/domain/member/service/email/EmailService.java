package com.timedeal.domain.member.service.email;

import com.timedeal.global.dto.BaseResponseStatus;
import com.timedeal.global.exception.custom.InvalidUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {


    private final EmailVerificationService emailVerificationService;
    private final EmailAsyncProcessor emailAsyncProcessor;

    public void sendEmailVerificationCode(String email) {
        validateEmailRequest(email);
        emailVerificationService.clearVerificationCode(email); // 이전 인증 코드 삭제
        String verificationCode = String.valueOf(new Random().nextInt(900000) + 100000);
        log.info("인증번호 생성: {}", verificationCode);

        emailAsyncProcessor.emailSendCode(email, verificationCode);
        emailVerificationService.storeVerificationCode(email, verificationCode);
    }


    public void verifyEmailCode(String email, String inputCode) {
        validateEmailVerification(email, inputCode);
        emailVerificationService.markAsVerified(email);
    }

    private void validateEmailRequest(String email) {
        if (emailVerificationService.isRequestLocked(email)) { // 이메일 요청 재발송 제한 잠금 설정 (10초 동안 잠금)
            throw new InvalidUserException(BaseResponseStatus.EMAIL_REQUEST_LOCKED);
        }

        if (emailVerificationService.isAlreadyVerified(email)) { // 인증 이미 완료
            throw new InvalidUserException(BaseResponseStatus.EMAIL_ALREADY_VERIFIED);
        }
    } // 이메일 전송 가드

    private void validateEmailVerification(String email, String inputCode) {
        if (emailVerificationService.isAlreadyVerified(email)) { // 인증 이미 완료
            throw new InvalidUserException(BaseResponseStatus.EMAIL_ALREADY_VERIFIED);
        }

        String storedCode = emailVerificationService.getStoredCode(email);
        if (storedCode == null || !storedCode.equals(inputCode)) {
            throw new InvalidUserException(BaseResponseStatus.EMAIL_INVALID_CODE);
        }
    } // 이메일 인증코드 가드
}
