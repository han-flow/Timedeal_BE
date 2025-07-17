package com.timedeal.domain.member.service.email;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailAsyncProcessor {

    private final JavaMailSender javaMailSender; // application.yml에서 설정해서 빈 등록
    private final MailProperties mailProperties;

    @Value("${email.fake-sending:false}")
    private boolean fakeSending;

    @Async("mailExecutor")
    @Retryable(noRetryFor = {IllegalArgumentException.class}, backoff = @Backoff(delay = 1000, multiplier = 2))
    public void emailSendCode(String toEmail, String verificationCode) {
        if (fakeSending) {
            log.info("[FAKE EMAIL] 인증 코드 {}가 {}로 전송된 것으로 간주합니다.", verificationCode, toEmail);
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom(mailProperties.getUsername());
        message.setSubject("이메일 인증 코드");
        message.setText("인증 코드는 " + verificationCode + " 입니다.");
        javaMailSender.send(message);
    }


}
