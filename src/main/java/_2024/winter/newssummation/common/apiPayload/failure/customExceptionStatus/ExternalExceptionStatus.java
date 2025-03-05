package _2024.winter.newssummation.common.apiPayload.failure.customExceptionStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExternalExceptionStatus {

    // [00 ~ 09]
    NAVER_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "50000", "네이버 서버 에러입니다. 서버관리자에게 문의하세요."),
    GEMINI_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "50000", "제미나이 서버 에러입니다. 서버관리자에게 문의하세요."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
