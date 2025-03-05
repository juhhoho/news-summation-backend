package _2024.winter.newssummation.domain.news.dto.response;

import _2024.winter.newssummation.domain.news.entity.News;
import lombok.*;

import java.util.List;
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetPagingNewsResponse {
    public int page;
    public int size;
    public int totalElements;
    List<News> newsList;
}
