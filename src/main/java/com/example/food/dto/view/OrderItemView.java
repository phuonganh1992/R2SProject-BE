package com.example.food.dto.view;

import com.example.food.domain.Order;
import com.example.food.domain.OrderItem;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OrderItemView implements Serializable {
    private Long id;
    private Integer quantity;
    private Float price;
    private FoodView food;
    private OrderView order;

    public static OrderItemView from(OrderItem domain){
        return OrderItemView.builder()
                .id(domain.getId())
                .quantity(domain.getQuantity())
                .price(domain.getPrice())
                .food(FoodView.from(domain.getFood()))
                .order(OrderView.from(domain.getOrder()))
                .build();
    }
}
