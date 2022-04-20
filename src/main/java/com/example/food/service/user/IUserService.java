package com.example.food.service.user;

import com.example.food.domain.User;
import com.example.food.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService, IGeneralService<User> {
}
