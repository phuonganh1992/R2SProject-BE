package com.example.food.dto.command;

import com.example.food.constant.Constant;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
public class MerchantRegisterCommand implements Serializable {
    @NotBlank
    @Size(min = 6, max = 50)
    @Pattern(regexp = "^[a-z]{1}[a-z0-9._-]{5,15}$")
    private String username;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    @Size(min = 3, max = 100)
    private String representative;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @Pattern(regexp = "^[+84]+[0-9]{9}$")
    private String phone;

    @NotBlank
    private String registrationCertificate;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$")
    private String taxIdentificationNumber;

    private Constant.ChannelName channel;
}
