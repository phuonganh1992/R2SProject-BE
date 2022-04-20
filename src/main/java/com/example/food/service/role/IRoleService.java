package com.example.food.service.role;

import com.example.food.domain.Role;
import com.example.food.service.IGeneralService;

public interface IRoleService extends IGeneralService<Role> {
    Role findByName(String name);
    Role create(String name);
}
