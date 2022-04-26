package com.example.food.dto.command;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
public class MerchantLoginCommand implements Serializable {
    @NotBlank(message = "username is not blank")
    private String username;
    @NotBlank(message = "password is not blank")
    private String password;

}
