package com.example.food.repository;

import com.example.food.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItem, Long> {
    Iterable<OrderItem> findAllByOrderId(Long id);
}
