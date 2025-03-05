package _2024.winter.newssummation.domain.news.service;

import _2024.winter.newssummation.domain.news.dto.response.GetNewsDetailResponse;
import _2024.winter.newssummation.external.feign.GetNaverNewsResponse;
import _2024.winter.newssummation.domain.news.dto.response.GetPagingNewsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NewsApplicationService {

    private final NewsCommandService newsCommandService;
    private final NewsQueryService newsQueryService;
    private final List<String> categoryList = new ArrayList<>(Arrays.asList("it"));

    // 하루에 한 번 실행되는 스케줄링 메서드
//    @Scheduled(cron = "0 0 0 * * *")  // 매일 자정에 실행
//    @Scheduled(cron = "0 0/1 * * * *")  // 매 1분마다 실행
    public void saveNaverNews() {
        for(String category: categoryList){
            GetNaverNewsResponse getNaverNewsResponse = newsQueryService.getNaverNews(category);
            newsCommandService.saveNaverNews(getNaverNewsResponse, category);
        }
    }

    public GetPagingNewsResponse getPagingNews(int page, int size, String category) {
        return newsQueryService.getPagingNews(page, size, category);
    }

    public GetNewsDetailResponse getNewsDetail(Long newsId) {
        newsCommandService.updateNewsSummary(newsId);
        return newsQueryService.getNewsDetail(newsId, newsCommandService.updateNewsSummary(newsId));
    }

}