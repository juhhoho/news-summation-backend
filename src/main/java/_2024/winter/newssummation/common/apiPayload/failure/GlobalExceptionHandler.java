package _2024.winter.newssummation.common.apiPayload.failure;

import _2024.winter.newssummation.common.apiPayload.failure.customException.ExternalException;
import _2024.winter.newssummation.common.apiPayload.failure.customException.NewsException;
import _2024.winter.newssummation.common.apiPayload.failure.customException.UserException;
import _2024.winter.newssummation.common.apiPayload.failure.customExceptionStatus.ExternalExceptionStatus;
import _2024.winter.newssummation.common.apiPayload.failure.customExceptionStatus.NewsExceptionStatus;
import _2024.winter.newssummation.common.apiPayload.failure.customExceptionStatus.UserExceptionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // [EXTERNAL]
    @ExceptionHandler(ExternalException.NaverServerException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(ExternalException.NaverServerException e){
        log.error("[GlobalExceptionHandler] ExternalException.NaverServerException occurred");

        return ResponseEntity
                .status(
                        ExternalExceptionStatus.NAVER_SERVER_ERROR.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, ExternalExceptionStatus.NAVER_SERVER_ERROR.getCode(), ExternalExceptionStatus.NAVER_SERVER_ERROR.getMessage()
                        )
                );
    }

    @ExceptionHandler(ExternalException.GeminiServerException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(ExternalException.GeminiServerException e){
        log.error("[GlobalExceptionHandler] ExternalException.GeminiServerException occurred");

        return ResponseEntity
                .status(
                        ExternalExceptionStatus.GEMINI_SERVER_ERROR.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, ExternalExceptionStatus.GEMINI_SERVER_ERROR.getCode(), ExternalExceptionStatus.GEMINI_SERVER_ERROR.getMessage()
                        )
                );
    }

    // [NEWS]
    @ExceptionHandler(NewsException.NewsNotExistException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(NewsException.NewsNotExistException e){
        log.error("[GlobalExceptionHandler] NewsException.NewsNotExistException occurred");

        return ResponseEntity
                .status(
                        NewsExceptionStatus.NEWS_NOT_EXIST.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, NewsExceptionStatus.NEWS_NOT_EXIST.getCode(), NewsExceptionStatus.NEWS_NOT_EXIST.getMessage()
                        )
                );
    }

    // [USER]
    @ExceptionHandler(UserException.UsernameDuplicateException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(UserException.UsernameDuplicateException e){
        log.error("[GlobalExceptionHandler] UserException.UsernameDuplicateException occurred");
        return ResponseEntity
                .status(
                        UserExceptionStatus.USERNAME_DUPLICATE.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, UserExceptionStatus.USERNAME_DUPLICATE.getCode(), UserExceptionStatus.USERNAME_DUPLICATE.getMessage()
                        )
                );
    }

    @ExceptionHandler(UserException.EmailDuplicateException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(UserException.EmailDuplicateException e){
        log.error("[GlobalExceptionHandler] UserException.EmailDuplicateException occurred");
        return ResponseEntity
                .status(
                        UserExceptionStatus.EMAIL_DUPLICATE.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, UserExceptionStatus.EMAIL_DUPLICATE.getCode(), UserExceptionStatus.EMAIL_DUPLICATE.getMessage()
                        )
                );
    }

    @ExceptionHandler(UserException.UsernameNotExistException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(UserException.UsernameNotExistException e){
        log.error("[GlobalExceptionHandler] UserException.UsernameNotExistException occurred");
        return ResponseEntity
                .status(
                        UserExceptionStatus.USERNAME_NOT_EXIST.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, UserExceptionStatus.USERNAME_NOT_EXIST.getCode(), UserExceptionStatus.USERNAME_NOT_EXIST.getMessage()
                        )
                );
    }

    @ExceptionHandler(UserException.PasswordNotValidException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(UserException.PasswordNotValidException e){
        log.error("[GlobalExceptionHandler] UserException.PasswordNotValidException occurred");
        return ResponseEntity
                .status(
                        UserExceptionStatus.PASSWORD_NOT_VALID.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, UserExceptionStatus.PASSWORD_NOT_VALID.getCode(), UserExceptionStatus.PASSWORD_NOT_VALID.getMessage()
                        )
                );
    }

    @ExceptionHandler(UserException.RefreshTokenNotValidException.class)
    public ResponseEntity<ExceptionApiResponse> handleException(UserException.RefreshTokenNotValidException e){
        log.error("[GlobalExceptionHandler] UserException.RefreshTokenNotValidException occurred");
        return ResponseEntity
                .status(
                        UserExceptionStatus.REFRESH_TOKEN_NOT_VALID.getHttpStatus()
                )
                .body(
                        new ExceptionApiResponse(
                                false, UserExceptionStatus.REFRESH_TOKEN_NOT_VALID.getCode(), UserExceptionStatus.REFRESH_TOKEN_NOT_VALID.getMessage()
                        )
                );
    }

}