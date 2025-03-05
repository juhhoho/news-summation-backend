package _2024.winter.newssummation.common.apiPayload.failure.customException;

import lombok.Data;

@Data
public class ExternalException {
    public static class NaverServerException extends RuntimeException{}
    public static class GeminiServerException extends RuntimeException{}

}
