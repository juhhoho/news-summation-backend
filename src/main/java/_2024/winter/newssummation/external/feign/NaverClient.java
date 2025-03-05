package _2024.winter.newssummation.external.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naverClient", url = "${external.naver.url}", configuration = NaverClientConfiguration.class)
public interface NaverClient {

    @GetMapping("/v1/search/news.json")
    GetNaverNewsResponse getNews(
            @RequestParam(name = "start") int start,
            @RequestParam(name = "display") int display,
            @RequestParam(name = "query") String query);
}
