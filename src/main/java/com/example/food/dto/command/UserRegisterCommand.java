package com.example.food.dto.command;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
public class UserRegisterCommand implements Serializable {
    @NotBlank(message = "Username must not be blank")
    @Size(min = 6, max = 50, message = "Username length from 6 to 50 characters ")
    @Pattern(regexp = "^[a-z]{1}[a-z0-9._-]{5,15}$")
    private String username;

    @Pattern(regexp = "^[+84]+[0-9]{9}$", message = "Phone have prefix +84 and postfix include 9 digits")
    private String phone;

    @NotBlank(message = "Email must not be blank")
    @Size(max = 50, message = "Email length maximum 50 characters")
    @Email(message = "Must follow by email pattern")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 100, message = "Password length from 8 to 100")
    private String password;
}
