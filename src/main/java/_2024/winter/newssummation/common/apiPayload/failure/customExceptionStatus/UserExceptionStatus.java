package _2024.winter.newssummation.common.apiPayload.failure.customExceptionStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserExceptionStatus {

    USERNAME_DUPLICATE(HttpStatus.BAD_REQUEST, "40000", "이미 존재하는 사용자이름입니다."),
    EMAIL_DUPLICATE(HttpStatus.BAD_REQUEST, "40001", "이미 존재하는 이메일입니다."),
    USERNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "40002", "존재하지 않는 사용자이름입니다."),
    PASSWORD_NOT_VALID(HttpStatus.BAD_REQUEST, "40003", "비밀번호가 일치하지 않습니다."),
    REFRESH_TOKEN_NOT_VALID(HttpStatus.BAD_REQUEST, "40004", "토큰이 유효하지 않습니다."),


    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}

