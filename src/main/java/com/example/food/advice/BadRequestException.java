package com.example.food.advice;

import com.example.food.dto.view.Response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BadRequestException extends CommonException {
    private Response response;
    private String message;

    public BadRequestException(String message) {
        super(Response.BAD_REQUEST, message);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append("\"responseCode\":").append("\"").append(this.getResponse().getResponseCode()).append("\"").append(",");
        stringBuilder.append("\"responseMessage\":").append("\"").append(this.getMessage() == null ? this.getResponse().getResponseMessage() : this.getMessage()).append("\"");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
