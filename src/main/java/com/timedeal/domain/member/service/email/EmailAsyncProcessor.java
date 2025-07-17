package com.timedeal.domain.member.service.email;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailAsyncProcessor {

    private final JavaMailSender javaMailSender; // application.yml에서 설정해서 빈 등록
    private final MailProperties mailProperties;
    private final SpringTemplateEngine templateEngine;

    @Value("${email.fake-sending:false}")
    private boolean fakeSending;

    @Async("mailExecutor")
    @Retryable(noRetryFor = {IllegalArgumentException.class}, backoff = @Backoff(delay = 1000, multiplier = 2))
    public void emailSendCode(String toEmail, String verificationCode) {
        if (fakeSending) {
            log.info("[FAKE EMAIL] 인증 코드 {}가 {}로 전송된 것으로 간주합니다.", verificationCode, toEmail);
            return;
        }

        try {
            //  HTML 렌더링
            Context context = new Context();
            context.setVariable("code", verificationCode);
            String htmlContent = templateEngine.process("email-verification", context);  // 템플릿 파일 이름

            //  메일 전송
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setFrom(mailProperties.getUsername());
            helper.setSubject("[HanFlow] 이메일 인증 코드 안내");
            helper.setText(htmlContent, true);  // HTML 모드

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 실패", e);
        }
    }

}
