package _2024.winter.newssummation.domain.user.controller;

import _2024.winter.newssummation.common.apiPayload.success.SuccessApiResponse;
import _2024.winter.newssummation.domain.user.dto.response.ReissueResponse;
import _2024.winter.newssummation.domain.user.service.UserApplicationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReissueController {

    private final UserApplicationService userApplicationService;

    @PostMapping("/reissue")
    public SuccessApiResponse<ReissueResponse> reissue(HttpServletRequest httpRequest, HttpServletResponse httpResponse){
        log.info("[ReissueController - refresh]");

        return SuccessApiResponse.onSuccessReissue(userApplicationService.reissue(httpRequest, httpResponse));
    }
}
