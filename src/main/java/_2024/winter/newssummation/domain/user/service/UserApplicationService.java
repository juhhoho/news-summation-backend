package _2024.winter.newssummation.domain.user.service;

import _2024.winter.newssummation.domain.user.dto.request.*;
import _2024.winter.newssummation.domain.user.dto.response.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserApplicationService {
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;
    private final EmailCommandService emailCommandService;
    private final EmailQueryService emailQueryService;
    // 인증메일 전송
    public SendAuthEmailResponse sendAuthEmail(SendAuthEmailRequest request){
        userQueryService.checkEmailDuplicate(request);
        return emailCommandService.sendAuthEmail(request);
    }

    // 인증메일 검증
    public CheckAuthEmailResponse checkAuthEmail(CheckAuthEmailRequest request){
        return emailQueryService.checkAuthEmail(request);
    }

    // 사용자이름 중복 확인
    public CheckUsernameDuplicateResponse checkUsernameDuplicate(CheckUsernameDuplicateRequest request){
        return userQueryService.checkUsernameDuplicate(request);
    }

    // 회원 가입
    public SignupResponse signup(SignupRequest request){
        return userCommandService.signup(request);
    }

    // 로그인
    public LoginResponse login(LoginRequest request, HttpServletResponse httpServletResponse){
        return userCommandService.login(request, httpServletResponse);
    }

    // 토큰 재발급
    public ReissueResponse reissue(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        return userCommandService.reissue(httpServletRequest, httpServletResponse);
    }

}
