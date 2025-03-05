package _2024.winter.newssummation.domain.user.service;

import _2024.winter.newssummation.common.apiPayload.failure.customException.UserException;
import _2024.winter.newssummation.domain.user.dto.request.LoginRequest;
import _2024.winter.newssummation.domain.user.dto.request.SignupRequest;
import _2024.winter.newssummation.domain.user.dto.response.LoginResponse;
import _2024.winter.newssummation.domain.user.dto.response.ReissueResponse;
import _2024.winter.newssummation.domain.user.dto.response.SignupResponse;
import _2024.winter.newssummation.domain.user.entity.User;
import _2024.winter.newssummation.domain.user.repository.UserRepository;
import _2024.winter.newssummation.domain.user.util.CookieUtil;
import _2024.winter.newssummation.domain.user.util.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserCommandService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate stringRedisTemplate;
    private final JWTUtil jwtUtil;

    // 회원가입
    public SignupResponse signup(SignupRequest request){
        log.info("[UserCommandService - signup]");

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .name(request.name)
                .role("ROLE_USER")
                .build();

        userRepository.saveAndFlush(user);

        return SignupResponse.builder()
                .userId(user.getId())
                .build();
    }

    // 로그인
    public LoginResponse login(LoginRequest request, HttpServletResponse httpServletResponse){
        log.info("[UserQueryService - login]");

        // 1. username 확인
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(UserException.UsernameNotExistException::new);

        // 2. password 일치 확인
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("비밀번호 불일치");
        }

        // 3. jwt token 발급 및 redis 저장
        String access = jwtUtil.createJwt("access", user.getUsername(), user.getRole(), 10 * 60 * 1000L);
        String refresh = jwtUtil.createJwt("refresh", user.getUsername(), user.getRole(), 24 * 60 * 60 * 1000L);

        String redisRefreshKey = "refresh:userId:" + user.getId();
        stringRedisTemplate.opsForValue().set(redisRefreshKey, refresh, 1, TimeUnit.DAYS);

        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Location, Set-Cookie");

        ResponseCookie responseCookie = ResponseCookie.from("refresh", refresh)
                .httpOnly(false)  // JavaScript에서 접근 불가
                .secure(true)    // HTTPS에서만 전송
                .sameSite("None") // CORS 환경에서 허용
                .path("/")
                .maxAge(Duration.ofDays(1))
                .build();
        httpServletResponse.setHeader("access", access);
        httpServletResponse.setHeader("Set-Cookie", responseCookie.toString());


        return LoginResponse.builder()
                .username(user.getUsername())
                .build();
    }

    // 토큰 재발급
    public ReissueResponse reissue(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        log.info("[UserQueryService - reissue]");

        // cookie refresh 추출
        String cookieRefresh = null;
        Cookie[] cookies = httpServletRequest.getCookies();

        for (Cookie cookie : cookies){
            if (cookie.getName().equals("refresh")){
                cookieRefresh = cookie.getValue();
            }
        }

        // redis refresh 추출
        User user = userRepository.findByUsername(jwtUtil.getUsername(cookieRefresh)).orElseThrow(UserException.PasswordNotValidException::new);
        String redisRefreshKey = "refresh:userId:" + user.getId();
        String redisRefreshValue = stringRedisTemplate.opsForValue().get(redisRefreshKey);

        // refresh 유효성 검증
        if (!jwtUtil.isValidRefreshToken(cookieRefresh, redisRefreshValue)){
            throw new UserException.RefreshTokenNotValidException();
        }

        // redis refresh 삭제
        stringRedisTemplate.delete(redisRefreshKey);

        // access, refresh 모두 재발급
        String newAccess = jwtUtil.createJwt("access", user.getUsername(), jwtUtil.getRole(cookieRefresh), 10 * 60 * 1000L);
        String newRefresh = jwtUtil.createJwt("refresh", user.getUsername(), jwtUtil.getRole(cookieRefresh), 24 * 60 * 60 * 1000L);

        // new refresh 1 day 유지
        stringRedisTemplate.opsForValue().set(redisRefreshKey, newRefresh, 1, TimeUnit.DAYS);

        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Access, Location");
        httpServletResponse.setHeader("access", newAccess);
        httpServletResponse.addCookie(CookieUtil.createCookie("refresh", newRefresh));


        // get localUser for return
        User reissuedUser =  userRepository.findByUsername(user.getUsername()).orElseThrow(RuntimeException::new);

        return ReissueResponse.builder()
                .userId(reissuedUser.getId())
                .build();
    }
}
