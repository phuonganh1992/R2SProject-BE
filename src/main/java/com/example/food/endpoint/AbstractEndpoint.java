package com.example.food.endpoint;

import com.example.food.dto.view.Response;
import com.example.food.dto.view.ResponseBody;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractEndpoint {
    public static final String HEADER_PAGE_COUNT = "X-Page-Count";
    public static final String HEADER_PAGE_NUMBER = "X-Page-Number";
    public static final String HEADER_PAGE_SIZE = "X-Page-Size";
    public static final String HEADER_TOTAL_COUNT = "X-Total-Count";

    private <T extends Serializable> HttpHeaders buildPagingHeaders(Page<T> page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_PAGE_COUNT, String.valueOf(page.getTotalPages()));
        headers.add(HEADER_PAGE_SIZE, String.valueOf(page.getSize()));
        headers.add(HEADER_TOTAL_COUNT, String.valueOf(page.getTotalElements()));
        headers.add(HEADER_PAGE_NUMBER, String.valueOf(page.getPageable().getPageNumber()));

        return headers;
    }

    public <T extends Serializable> ResponseEntity<ResponseBody<List<T>>> buildPagingResponse(@NonNull Page<T> page) {
        return ResponseEntity.ok()
                .headers(buildPagingHeaders(page))
                .body(new ResponseBody(Response.SUCCESS, page.getContent()));
    }
}
