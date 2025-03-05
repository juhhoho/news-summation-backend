package _2024.winter.newssummation.domain.news.repository;

import _2024.winter.newssummation.external.feign.GetNaverNewsResponse;
import _2024.winter.newssummation.domain.news.entity.News;
import _2024.winter.newssummation.domain.news.entity.QNews;
import _2024.winter.newssummation.external.feign.NaverClient;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class NewsCustomRepositoryImpl implements NewsCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final NaverClient naverClient;

    @Override
    public GetNaverNewsResponse getNaverNews(int start, int display, String query) {
        return naverClient.getNews(start, display, query);

    }

    @Override
    public Page<News> categoryNews(String category, Pageable pageable) {
        QNews news =  QNews.news;

        List<News> newsList = jpaQueryFactory.selectFrom(news)
                .where(news.category.eq(category))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(news.pubDate.desc())
                .fetch();

        long total = Optional.ofNullable(jpaQueryFactory.select(news.count())
                .from(news)
                .where(news.category.eq(category))
                .fetchOne()).orElse(0L);

        return new PageImpl<>(newsList, pageable, total);
    }

    @Override
    @Transactional
    public void updateSummary(News oldNews, String summary) {
        QNews news =  QNews.news;

        jpaQueryFactory.update(news)
                .set(news.summary, summary)
                .where(news.eq(oldNews))
                .execute();
    }
}
