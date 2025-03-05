package _2024.winter.newssummation.external.gemini;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetSummaryRequest {
    private String task;
    private String text;
}