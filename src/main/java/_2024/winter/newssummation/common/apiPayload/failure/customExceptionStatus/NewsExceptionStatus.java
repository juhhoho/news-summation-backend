package _2024.winter.newssummation.common.apiPayload.failure.customExceptionStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NewsExceptionStatus {

    // [10 ~ 19]
    NEWS_NOT_EXIST(HttpStatus.BAD_REQUEST, "40000", "존재하지 않는 뉴스입니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}