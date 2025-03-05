package _2024.winter.newssummation.domain.news.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "news")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class News {
    @Id
    @Column(name = "news_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "link")
    private String link;

    @Column(name = "description")
    private String description;

    @Column(name = "pubDate")
    private String pubDate;

    @Column(name = "originalLink")
    private String originalLink;

    @Column(name = "category")
    private String category;

    @Lob
    @Column(name = "articleBody", columnDefinition = "TEXT")
    private String articleBody;

    @Column(name = "summary")
    private String summary;

    @Builder
    public News(String title, String link, String description, String pubDate, String originalLink,  String category, String articleBody, String summary) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;
        this.originalLink = originalLink;
        this.category = category;
        this.articleBody = articleBody;
        this.summary = summary;
    }

}
