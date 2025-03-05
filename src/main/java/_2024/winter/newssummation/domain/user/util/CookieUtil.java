package _2024.winter.newssummation.domain.user.util;

import jakarta.servlet.http.Cookie;

public class CookieUtil {
    public static Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);

        cookie.setMaxAge(24 * 60 * 60); // 쿠키 만료 시간 (1일)
        cookie.setHttpOnly(false);       // true -> JavaScript 접근 차단 (보안 강화)
        cookie.setSecure(false);         // true -> HTTPS에서만 전송
        cookie.setPath("/");            // 모든 경로에서 접근 가능
        cookie.setAttribute("SameSite", "None");

        return cookie;
    }
}
