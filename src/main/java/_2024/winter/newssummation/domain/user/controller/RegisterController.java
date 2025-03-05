package _2024.winter.newssummation.domain.user.controller;

import _2024.winter.newssummation.common.apiPayload.success.SuccessApiResponse;
import _2024.winter.newssummation.domain.user.dto.request.CheckAuthEmailRequest;
import _2024.winter.newssummation.domain.user.dto.request.CheckUsernameDuplicateRequest;
import _2024.winter.newssummation.domain.user.dto.request.SendAuthEmailRequest;
import _2024.winter.newssummation.domain.user.dto.request.SignupRequest;
import _2024.winter.newssummation.domain.user.dto.response.CheckAuthEmailResponse;
import _2024.winter.newssummation.domain.user.dto.response.CheckUsernameDuplicateResponse;
import _2024.winter.newssummation.domain.user.dto.response.SendAuthEmailResponse;
import _2024.winter.newssummation.domain.user.dto.response.SignupResponse;
import _2024.winter.newssummation.domain.user.service.UserApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class RegisterController {

    private final UserApplicationService userApplicationService;

    // 1. auth-email 전송
    @PostMapping("/register/email/send")
    public SuccessApiResponse<SendAuthEmailResponse> sendAuthEmail(@RequestBody SendAuthEmailRequest request) {
        log.info("[[RegisterController - AuthEmail send] request.email = {}", request.getEmail());

        return SuccessApiResponse.onSuccessSendAuthEmail(userApplicationService.sendAuthEmail(request));
    }

    // 2. auth-email code 검증
    @PostMapping("/register/email/check")
    public SuccessApiResponse<CheckAuthEmailResponse> checkAuthEmail(@RequestBody CheckAuthEmailRequest request) {
        log.info("[[RegisterController - AuthEmail check] request.email = {}, request.verificationCode = {}", request.getEmail(), request.getVerificationCode());

        return SuccessApiResponse.onSuccessCheckAuthEmail(userApplicationService.checkAuthEmail(request));
    }

    // 3. username 중복 확인
    @PostMapping("/register/check/username")
    public SuccessApiResponse<CheckUsernameDuplicateResponse> checkUsernameDuplicate(@RequestBody CheckUsernameDuplicateRequest request){
        log.info("[RegisterController - checkUsernameDuplicate] request.username = {}", request.getUsername());

        return SuccessApiResponse.onSuccessCheckUsernameDuplicate(userApplicationService.checkUsernameDuplicate(request));
    }

    // 4. 회원 가입
    @PostMapping("/register/signup")
    public SuccessApiResponse<SignupResponse> signup(@RequestBody SignupRequest request){
        log.info("[RegisterController - register] request = {}", request);
        return SuccessApiResponse.onSuccessSignup(userApplicationService.signup(request));
    }
}