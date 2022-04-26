package com.example.food.repository;

import com.example.food.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserNameAndId (String username, Long id);
}
