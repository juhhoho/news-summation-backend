package _2024.winter.newssummation.common.apiPayload.success;

import _2024.winter.newssummation.common.apiPayload.BaseApiResponse;
import _2024.winter.newssummation.domain.news.dto.response.GetNewsDetailResponse;
import _2024.winter.newssummation.domain.news.dto.response.GetPagingNewsResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SuccessApiResponse <T> extends BaseApiResponse {
    private final T response;

    public SuccessApiResponse(Boolean isSuccess, String code, String message, T response) {
        super(isSuccess, code, message);
        this.response = response;
    }

    // [NEWS]
    public static SuccessApiResponse<GetPagingNewsResponse> onSuccessGetPagingNews(GetPagingNewsResponse response){
        return new SuccessApiResponse<>(true, HttpStatus.OK.toString()
                , "페이징된 뉴스 조회 성공", response);
    }

    public static SuccessApiResponse<Void> onSuccessSaveNaverNews(){
        return new SuccessApiResponse<>(true, HttpStatus.OK.toString()
                , "뉴스 조회 성공", null);
    }

    public static SuccessApiResponse<GetNewsDetailResponse> onSuccessGetNewsDetail(GetNewsDetailResponse response){
        return new SuccessApiResponse<>(true, HttpStatus.OK.toString()
                , "뉴스 조회 성공", response);
    }

}
