package _2024.winter.newssummation.domain.news.service;

import _2024.winter.newssummation.common.apiPayload.failure.customException.NewsException;
import _2024.winter.newssummation.external.gemini.GeminiService;
import _2024.winter.newssummation.external.gemini.GetSummaryRequest;
import _2024.winter.newssummation.external.feign.Item;
import _2024.winter.newssummation.external.feign.GetNaverNewsResponse;
import _2024.winter.newssummation.domain.news.entity.News;
import _2024.winter.newssummation.domain.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NewsCommandService {

    private final NewsRepository newsRepository;
    private final GeminiService geminiService;

    public void saveNaverNews(GetNaverNewsResponse getNaverNewsResponse, String category){
        log.info("[NewsCommandService - saveNaverNews]");

        for(Item item : getNaverNewsResponse.getItems().stream().toList()){
            if (!item.getLink().startsWith("https://n.news.naver.com/")){
                continue;
            }

            String articleBody = crawlNewsBody(item.getLink());

            News news = News.builder()
                    .title(item.getTitle())
                    .description(item.getDescription())
                    .link(item.getLink())
                    .originalLink(item.getOriginalLink())
                    .pubDate(item.getPubDate())
                    .category(category)
                    .articleBody(articleBody)
                    .summary("")
                    .build();

            newsRepository.save(news);
        }
    }

    // 네이버 뉴스 본문을 크롤링
    private String crawlNewsBody(String articleUrl) {
        try {
            // URL에서 HTML을 가져와서 Jsoup으로 파싱
            Document document = Jsoup.connect(articleUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36") // User-Agent 설정
                    .get();

            // 본문을 포함한 div 태그를 찾기
            Element articleBodyElement = document.selectFirst("div#newsct_article");
            if (articleBodyElement != null) {
                // 본문 텍스트를 추출하여 반환
                return articleBodyElement.text();
            } else {
                log.warn("본문을 찾을 수 없습니다. URL: " + articleUrl);
                return "본문을 찾을 수 없습니다.";
            }
        } catch (IOException e) {
            log.error("네이버 뉴스 본문 크롤링 중 오류 발생. URL: " + articleUrl, e);
            return "본문을 크롤링하는 데 오류가 발생했습니다.";
        }
    }

    public String updateNewsSummary(Long newsId) {
        log.info("[NewsCommandService - updateNewsSummary]");

        News news = newsRepository.findById(newsId).orElseThrow(NewsException.NewsNotExistException::new);

        if (news.getSummary().isEmpty()) {
            String summary = geminiService.summarizeText(
                    GetSummaryRequest.builder()
                            .task("Please summarize the following news article in Korean in 100 characters or less.")
                            .text(news.getArticleBody())
                            .build()
            );

            newsRepository.updateSummary(news, summary);
            return summary;
        }
        return news.getSummary();
    }
}
