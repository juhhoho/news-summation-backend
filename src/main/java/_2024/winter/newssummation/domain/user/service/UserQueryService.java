package _2024.winter.newssummation.domain.user.service;

import _2024.winter.newssummation.common.apiPayload.failure.customException.UserException;
import _2024.winter.newssummation.domain.user.dto.request.CheckUsernameDuplicateRequest;
import _2024.winter.newssummation.domain.user.dto.request.SendAuthEmailRequest;
import _2024.winter.newssummation.domain.user.dto.response.CheckUsernameDuplicateResponse;
import _2024.winter.newssummation.domain.user.repository.UserRepository;
import _2024.winter.newssummation.domain.user.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserQueryService {
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    // 사용자이름 중복 확인
    public CheckUsernameDuplicateResponse checkUsernameDuplicate(CheckUsernameDuplicateRequest request){
        log.info("[UserQueryService - checkUsernameDuplicate]");


        if(userRepository.existsByUsername(request.getUsername())){
            throw new UserException.UsernameDuplicateException();
        }

        return CheckUsernameDuplicateResponse.builder()
                .username(request.getUsername())
                .build();
    }

    // 이메일 중복 확인
    public void checkEmailDuplicate(SendAuthEmailRequest request){
        log.info("[UserQueryService - checkEmailDuplicate]");

        if (userRepository.existsByEmail(request.getEmail())){
            throw new UserException.EmailDuplicateException();
        }
    }
}
