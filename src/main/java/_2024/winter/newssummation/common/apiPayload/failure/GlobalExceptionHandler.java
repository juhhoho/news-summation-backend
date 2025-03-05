package _2024.winter.newssummation.common.apiPayload.failure;

import _2024.winter.newssummation.common.apiPayload.failure.customException.ExternalException;
import _2024.winter.newssummation.common.apiPayload.failure.customException.NewsException;
import _2024.winter.newssummation.common.apiPayload.failure.customExceptionStatus.ExternalExceptionStatus;
import _2024.winter.newssummation.common.apiPayload.failure.customExceptionStatus.NewsExceptionStatus;
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
}