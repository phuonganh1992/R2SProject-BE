package com.example.food.dto.view;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class CartView implements Serializable {
    private List<CartItemView> items;
    private Integer totalQuantity;
    private Double totalAmount;



}
