package _2024.winter.newssummation.domain.news.repository;

import _2024.winter.newssummation.external.feign.GetNaverNewsResponse;
import _2024.winter.newssummation.domain.news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


@Repository
public interface NewsCustomRepository {
    GetNaverNewsResponse getNaverNews(int start, int display, String query);
    Page<News> categoryNews(String category, Pageable pageable);
    void updateSummary(News oldNews, String summary);
}
