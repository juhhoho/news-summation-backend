package _2024.winter.newssummation.domain.news.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetNewsDetailResponse {
    private Long id;
    private String title;
    private String link;
    private String pubDate;
    private String category;
    private String summary;
}
