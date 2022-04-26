package com.example.food.service.role;

import com.example.food.domain.Role;
import com.example.food.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    public Role create(String name) {
        Role role=new Role(name);
        return roleRepository.save(role);
    }
}
