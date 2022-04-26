package com.example.food.repository;

import com.example.food.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
    Iterable<CartItem> findAllByUsername(String username);
    Boolean existsByUsernameAndFoodId(String username, Long id);
    Optional<CartItem> findFirstByUsernameAndFoodId(String username, Long id);
    Iterable<CartItem> findAllByUsernameAndIdIn(String username, List<Long> ids);
}
