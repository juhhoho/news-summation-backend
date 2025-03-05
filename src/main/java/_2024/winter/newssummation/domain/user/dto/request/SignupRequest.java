package _2024.winter.newssummation.domain.user.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
public class SignupRequest {
    public String username;
    public String password;
    public String email;
    public String name;
    public String phone;

    @Builder
    public SignupRequest(String username, String password, String email, String name, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }
}
