package com.example.food.dto.view;

import com.example.food.constant.Constant;
import com.example.food.domain.Order;
import com.example.food.domain.OrderItem;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
public class OrderView implements Serializable {
    private Long id;
    private Instant orderTime;
    private Constant.OrderStatus status;
    private String address;
    private Double totalAmount;
    private UserView user;
    private String merchant;

    public static OrderView from(Order order) {
        return OrderView.builder()
                .id(order.getId())
                .orderTime(order.getOrderTime())
                .status(order.getStatus())
                .address(order.getAddress())
                .totalAmount(order.getTotalAmount())
                .user(UserView.from(order.getUser()))
                .merchant(order.getMerchant().getName())
                .build();
    }
}
