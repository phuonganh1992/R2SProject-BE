package com.example.food.dto.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBody<T> implements Serializable {
    private String responseCode;
    private String responseMessage;
    private T responseData;

    public ResponseBody(Response response, T data) {
        this.responseCode = response.getResponseCode();
        this.responseMessage = response.getResponseMessage();
        this.responseData = data;
    }
}
