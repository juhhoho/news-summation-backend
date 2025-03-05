package _2024.winter.newssummation.common.config;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE) // CORS 필터를 최우선으로 실행
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 모든 오리진 허용
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Location", "access", "Set-Cookie"));
        config.setAllowCredentials(true); // 인증 관련 요청 허용

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE) // CORS 필터를 최우선으로 실행
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//
//        // FlutterFlow의 API 호출 도메인을 여기에 추가
//        config.setAllowedOrigins(List.of(
//                "http://localhost:3000",  // 로컬 React 개발 환경
//                "https://app.flutterflow.io", // FlutterFlow 개발 환경
//                "https://your-flutterflow-app-url.com" // 배포된 FlutterFlow 앱 도메인 (필요 시 추가)
//        ));
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//        config.setAllowedHeaders(List.of("*"));
//        config.setExposedHeaders(List.of("Location", "access", "Set-Cookie"));
//        config.setAllowCredentials(true); // 인증 관련 요청 허용
//
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
}