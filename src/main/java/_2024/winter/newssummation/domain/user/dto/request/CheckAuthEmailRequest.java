package _2024.winter.newssummation.domain.user.dto.request;

import lombok.Data;

@Data
public class CheckAuthEmailRequest {
    public String email;
    public String verificationCode;
}
