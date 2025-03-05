package _2024.winter.newssummation.domain.user.service;

import _2024.winter.newssummation.common.config.RedisConfig;
import _2024.winter.newssummation.domain.user.dto.request.CheckAuthEmailRequest;
import _2024.winter.newssummation.domain.user.dto.response.CheckAuthEmailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailQueryService {

    private final RedisConfig redisConfig;

    @Value("${spring.mail.username}")
    private String serviceName;

    // 이메일로 전송된 인증 번호 확인
    public CheckAuthEmailResponse checkAuthEmail(CheckAuthEmailRequest request) {
        log.info("[EmailQueryService - checkAuthEmail]");
        ValueOperations<String, String> valOperations = redisConfig.redisTemplate().opsForValue();
        String code = valOperations.get(request.getEmail());
        
        if (Objects.equals(code, request.getVerificationCode())) {
            return CheckAuthEmailResponse.builder()
                    .email(request.getEmail())
                    .build();
        }

        throw new RuntimeException("인증 실패");
    }
}
