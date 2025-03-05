package _2024.winter.newssummation.domain.user.controller;

import _2024.winter.newssummation.common.apiPayload.success.SuccessApiResponse;
import _2024.winter.newssummation.domain.user.dto.request.LoginRequest;
import _2024.winter.newssummation.domain.user.dto.response.LoginResponse;
import _2024.winter.newssummation.domain.user.service.UserApplicationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final UserApplicationService userApplicationService;

    @PostMapping("/login")
    public SuccessApiResponse<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse httpServletResponse) {
        log.info("[LoginController - login] request.username = {},request.password = {} ", request.getUsername(), request.getPassword());

        return SuccessApiResponse.onSuccessLogin(userApplicationService.login(request, httpServletResponse));
    }
}