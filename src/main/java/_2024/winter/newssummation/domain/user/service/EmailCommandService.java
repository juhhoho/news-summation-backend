package _2024.winter.newssummation.domain.user.service;

import _2024.winter.newssummation.common.config.RedisConfig;
import _2024.winter.newssummation.domain.user.dto.request.SendAuthEmailRequest;
import _2024.winter.newssummation.domain.user.dto.response.SendAuthEmailResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EmailCommandService {
    private final JavaMailSender javaMailSender;
    private final RedisConfig redisConfig;

    @Value("${spring.mail.username}")
    private String serviceName;

    /* 인증 이메일 전송 */
    public SendAuthEmailResponse sendAuthEmail(SendAuthEmailRequest request) {
        log.info("[EmailCommandService - sendAuthEmail]");

        int verificationCode = makeRandomNum();

        // 이메일 제목, 내용
        String title = "[Welcome TWGING!] 회원 가입 이메일";
        String content =
                "이메일을 인증하기 위한 절차입니다." +
                        "<br><br>" +
                        "인증 번호는 " + verificationCode + "입니다." +
                        "<br>" +
                        "회원 가입 폼에 해당 번호를 입력해주세요.";
        // 이메일 전송
        mailSend(serviceName, request.getEmail(), title, content, verificationCode);

        // 인증 번호 반환
        return SendAuthEmailResponse.builder()
                .verificationCode(Integer.toString(verificationCode))
                .build();
    }
    /* 랜덤 인증번호 생성 */
    public int makeRandomNum() {
        Random r = new Random();
        String randomNumber = "";
        for(int i = 0; i < 6; i++) {
            randomNumber += Integer.toString(r.nextInt(10));
        }

        return Integer.parseInt(randomNumber);
    }

    /* 이메일 전송 */
    public void mailSend(String setFrom, String toMail, String title, String content, int verificationCode) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
            helper.setFrom(setFrom); // service name
            helper.setTo(toMail); // customer email
            helper.setSubject(title); // email title
            helper.setText(content,true); // content, html: true
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // 에러 출력
        }

        // redis에 3분 동안 이메일과 인증 코드 저장
        ValueOperations<String, String> valOperations = redisConfig.redisTemplate().opsForValue();
        valOperations.set(toMail, Integer.toString(verificationCode), 3, TimeUnit.MINUTES);
    }
}
