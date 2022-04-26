package com.example.food.repository;

import com.example.food.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFoodRepository extends JpaRepository<Food, Long> {
    Iterable<Food> findAllByMerchantId(Long id);

    Iterable<Food> findAllByIdIn(List<Long> ids);
}
