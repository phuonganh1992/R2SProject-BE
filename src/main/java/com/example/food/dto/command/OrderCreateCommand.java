package com.example.food.dto.command;

import com.example.food.domain.CartItem;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class OrderCreateCommand implements Serializable {
    /**
     * Id cua card item
     */
    @NotEmpty(message = "list item is not empty")
    private List<Long> items;
    /**
     * Address use select in order
     */
    private String address;
}
