package com.example.food.dto.view;

import com.example.food.domain.CartItem;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CartItemView implements Serializable {
    private Long id;
    private Long foodId;
    private String foodName;
    private Float price;
    private String username;
    private Integer quantity;

    public static CartItemView from(CartItem domain){
        return CartItemView.builder()
                .id(domain.getId())
                .foodId(domain.getFoodId())
                .foodName(domain.getFoodName())
                .price(domain.getPrice())
                .username(domain.getUsername())
                .quantity(domain.getQuantity())
                .build();
    }


}
