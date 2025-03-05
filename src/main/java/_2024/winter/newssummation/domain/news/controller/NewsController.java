package _2024.winter.newssummation.domain.news.controller;

import _2024.winter.newssummation.common.apiPayload.success.SuccessApiResponse;
import _2024.winter.newssummation.domain.news.dto.response.GetNewsDetailResponse;
import _2024.winter.newssummation.domain.news.dto.response.GetPagingNewsResponse;
import _2024.winter.newssummation.domain.news.service.NewsApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class NewsController {
    private final NewsApplicationService newsApplicationService;

    // 네이버 뉴스 저장 - naver open api
    /*
        saveNaverNews는 테스트용도의 컨트롤러
        서비스로직만 나중에 @Scheduled 해줄것!
        그리고 Controller와 SuccessApiResponse.onSuccessSaveNaverNews는 없애주기!
     */
    @GetMapping("/news/save")
    public SuccessApiResponse<Void> saveNaverNews()
    {
        log.info("[NewsController - saveNaverNews]");

        newsApplicationService.saveNaverNews();
        return SuccessApiResponse.onSuccessSaveNaverNews();
    }

    // 카테고리 기반 네이버 뉴스 리스트 조회
    @GetMapping("/news")
    public SuccessApiResponse<GetPagingNewsResponse> getPagingNews(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam("category") String category)
    {
        log.info("[NewsController - getNews] page = {}, size = {}, category = {}", page , size, category);

        return SuccessApiResponse.onSuccessGetPagingNews(newsApplicationService.getPagingNews(page, size, category));
    }

    // 뉴스 디테일 조회
    @GetMapping("/news/{newsId}")
    public SuccessApiResponse<GetNewsDetailResponse> getNewsDetail(
            @PathVariable("newsId") Long newsId)
    {
        log.info("[NewsController - getNewsDetail] newsId = {}", newsId);

        return SuccessApiResponse.onSuccessGetNewsDetail(newsApplicationService.getNewsDetail(newsId));
    }

}
