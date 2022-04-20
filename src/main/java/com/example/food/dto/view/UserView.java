package com.example.food.dto.view;

import com.example.food.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class UserView implements Serializable {
    private UUID id;
    @Schema(description = "username")
    private String username;
    private String name;
    private String email;
    private String phone;
    private String avatar;
    private String status;
    private String channel;

    public static UserView from(User domain){
        return UserView.builder()
                .id(domain.getId())
                .username(domain.getUsername())
                .name(domain.getName())
                .email(domain.getEmail())
                .phone(domain.getPhone())
                .avatar(domain.getAvatar())
                .status(domain.getStatus().name())
                .channel(domain.getChannel().name())
                .build();
    }
}
