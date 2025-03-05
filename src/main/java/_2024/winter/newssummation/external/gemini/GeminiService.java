package _2024.winter.newssummation.external.gemini;

import _2024.winter.newssummation.common.apiPayload.failure.customException.ExternalException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GeminiService {

    private final WebClient webClient;

    @Value("${external.gemini.api-key}")
    private String apiKey;

    @Value("${external.gemini.api-url}")
    private String API_URL;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String summarizeText(GetSummaryRequest request) {
        String task = request.getTask();
        String text = request.getText();
        String combinedText = "Task: " + task + "\nText: " + text;

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(Map.of(
                        "parts", List.of(Map.of("text", combinedText))
                ))
        );

        Map<String, Object> response = webClient.post()
                .uri(API_URL + "?key=" + apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block(); // 동기 처리

        return extractSummary(response);
    }

    private String extractSummary(Map<String, Object> response) {
        return Optional.ofNullable((List<Map<String, Object>>) response.get("candidates"))
                .filter(candidates -> !candidates.isEmpty())
                .map(candidates -> candidates.get(0))
                .map(candidate -> (Map<String, Object>) candidate.get("content"))
                .map(content -> (List<Map<String, String>>) content.get("parts"))
                .filter(parts -> !parts.isEmpty())
                .map(parts -> parts.get(0).get("text"))
                .orElseThrow(ExternalException.GeminiServerException::new);
    }
}