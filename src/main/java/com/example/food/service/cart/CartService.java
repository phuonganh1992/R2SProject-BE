package com.example.food.service.cart;

import com.example.food.advice.CommonException;
import com.example.food.domain.CartItem;
import com.example.food.domain.Food;
import com.example.food.domain.User;
import com.example.food.dto.view.CartItemView;
import com.example.food.dto.view.CartView;
import com.example.food.dto.view.Response;
import com.example.food.repository.ICartItemRepository;
import com.example.food.repository.IFoodRepository;
import com.example.food.repository.IUserRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartService {
    @Autowired
    private ICartItemRepository cartItemRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IFoodRepository foodRepository;

    public CartView showCart(@NonNull String username) {
        Iterable<CartItem> cartIterable = cartItemRepository.findAllByUsername(username);
        List<CartItem> carts = IterableUtils.toList(cartIterable);
        List<CartItemView> items = carts.stream().map(item -> CartItemView.from(item))
                .collect(Collectors.toList());
        int totalQuantity = carts.stream().mapToInt(CartItem::getQuantity).sum();
        double totalAmount = carts.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();

        return CartView.builder()
                .items(items)
                .totalAmount(totalAmount)
                .totalQuantity(totalQuantity)
                .build();
    }

    public CartView plusFoodInCart(@NonNull String username, @NonNull Long foodId) {
        Optional<User> optionalUser = userRepository.findFirstByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        User user = optionalUser.get();

        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if (!optionalFood.isPresent()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        Food food = optionalFood.get();

        Boolean existFoodInCart = cartItemRepository.existsByUsernameAndFoodId(username, foodId);

        if (existFoodInCart) {
            CartItem cartItem = cartItemRepository.findFirstByUsernameAndFoodId(username, foodId).get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemRepository.save(cartItem);
        } else {
            CartItem newCartItem = CartItem.builder()
                    .foodId(food.getId())
                    .foodName(food.getName())
                    .price(food.getPrice())
                    .quantity(1)
                    .username(user.getUsername())
                    .createdAt(Instant.now())
                    .createdBy(username)
                    .build();
            cartItemRepository.save(newCartItem);
        }
        return showCart(username);
    }

    public CartView minusFoodInCart(@NonNull String username, @NonNull Long foodId) {
        Optional<User> optionalUser = userRepository.findFirstByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }

        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if (!optionalFood.isPresent()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }

        Boolean existFoodInCart = cartItemRepository.existsByUsernameAndFoodId(username, foodId);

        if (existFoodInCart) {
            CartItem cartItem = cartItemRepository.findFirstByUsernameAndFoodId(username, foodId).get();
            if(cartItem.getQuantity()>1){
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cartItemRepository.save(cartItem);
            } else if(cartItem.getQuantity()==1){
                cartItemRepository.delete(cartItem);
            }
        } else {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        return showCart(username);
    }
}
