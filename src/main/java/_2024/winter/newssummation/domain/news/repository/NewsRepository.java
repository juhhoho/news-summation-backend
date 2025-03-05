package _2024.winter.newssummation.domain.news.repository;

import _2024.winter.newssummation.domain.news.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>, NewsCustomRepository {
}
