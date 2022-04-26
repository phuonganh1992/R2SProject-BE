package com.example.food.dto.view;

import com.example.food.constant.Constant;
import com.example.food.domain.Category;
import com.example.food.domain.Food;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@Builder
public class FoodView implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Float preparedTime;
    private Float price;
    private Constant.FoodStatus status;
    private List<String> images;
    private String merchant;
    private Category category;
    private Instant createdAt;
    private String createdBy;
    private Instant modifiedAt;
    private String modifiedBy;

    public static FoodView from(Food domain){
        return FoodView.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .preparedTime(domain.getPreparedTime())
                .price(domain.getPrice())
                .status(domain.getStatus())
                .images(domain.getImages())
                .merchant(domain.getMerchant().getName())
                .category(domain.getCategory())
                .createdAt(domain.getCreatedAt())
                .createdBy(domain.getCreatedBy())
                .modifiedAt(domain.getModifiedAt())
                .modifiedBy(domain.getModifiedBy())
                .build();
    }
}
