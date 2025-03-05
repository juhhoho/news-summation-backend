package _2024.winter.newssummation.external.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {
    private String title;
    @JsonProperty("originallink")
    private String originalLink;
    private String link;
    private String description;
    private String pubDate;

}
