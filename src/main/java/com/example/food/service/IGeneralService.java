package com.example.food.service;

import java.util.UUID;

public interface IGeneralService<T> {
    T save(T t);

    void delete(UUID id);
}
