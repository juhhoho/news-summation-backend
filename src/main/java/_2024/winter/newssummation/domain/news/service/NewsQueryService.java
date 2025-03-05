package _2024.winter.newssummation.domain.news.service;

import _2024.winter.newssummation.common.apiPayload.failure.customException.NewsException;
import _2024.winter.newssummation.domain.news.dto.response.GetNewsDetailResponse;
import _2024.winter.newssummation.external.feign.GetNaverNewsResponse;
import _2024.winter.newssummation.domain.news.dto.response.GetPagingNewsResponse;
import _2024.winter.newssummation.domain.news.entity.News;
import _2024.winter.newssummation.domain.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsQueryService {

    private final NewsRepository newsRepository;

    GetNaverNewsResponse getNaverNews(String query) {
        log.info("[NewsQueryService - getNaverNews]");

        return newsRepository.getNaverNews(1, 100, query);
    }

    GetPagingNewsResponse getPagingNews(int page, int size, String category) {
        log.info("[NewsQueryService - getNews]");

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "pubDate"));
        Page<News> pagingNews = newsRepository.categoryNews(category, pageable);

        return GetPagingNewsResponse.builder()
                .page(page)
                .size(size)
                .totalElements((int) pagingNews.getTotalElements())
                .newsList(pagingNews.stream().toList())
                .build();
    }
    public GetNewsDetailResponse getNewsDetail(Long newsId, String summary) {
        log.info("[NewsQueryService - getNewsDetail]");
        News news = newsRepository.findById(newsId).orElseThrow(NewsException.NewsNotExistException::new);

        return GetNewsDetailResponse.builder()
                .id(news.getId())
                .title(news.getTitle())
                .link(news.getLink())
                .pubDate(news.getPubDate())
                .category(news.getCategory())
                .summary(summary)
                .build();
    }

}
