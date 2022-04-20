package com.example.food.dto.command;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
public class UserLoginCommand implements Serializable {
    @NotBlank(message = "username must not blank")
    private String username;
    @NotBlank(message = "password must not blank")
    private String password;
}
