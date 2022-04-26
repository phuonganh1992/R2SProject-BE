package com.example.food.dto.command;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserUpdateCommand extends PatchRequestCommand{
    public String getName() {
        return as(String.class, patchRequestData.get("name"));
    }

    public void setName(String name) {
        patchRequestData.put("name", name);
    }

    public String getPhone() {
        return as(String.class, patchRequestData.get("phone"));
    }

    public void setPhone(String phone) {
        patchRequestData.put("phone", phone);
    }

    public String getAvatar() {
        return as(String.class, patchRequestData.get("avatar"));
    }

    public void setAvatar(String avatar) {
        patchRequestData.put("avatar", avatar);
    }
}
