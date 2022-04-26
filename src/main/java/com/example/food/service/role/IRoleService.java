package com.example.food.service.role;

import com.example.food.domain.Role;

public interface IRoleService{
    Role findByName(String name);
    Role create(String name);
}
