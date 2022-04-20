package com.example.food.dto.command;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MerchantLoginCommand implements Serializable {
    private String username;
    private String password;

}
