package com.example.food.service.order;

import com.example.food.advice.CommonException;
import com.example.food.constant.Constant;
import com.example.food.domain.*;
import com.example.food.dto.command.OrderCreateCommand;
import com.example.food.dto.view.OrderView;
import com.example.food.dto.view.Response;
import com.example.food.repository.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private ICartItemRepository cartItemRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IFoodRepository foodRepository;

    @Autowired
    private IOrderItemRepository orderItemRepository;

    public OrderView findByUsernameAndId(@NonNull String username, @NonNull Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        Order order = optionalOrder.get();
        if (!order.getUser().getUsername().equals(username))
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        else
            return OrderView.from(order);
    }

    public OrderView create(@NonNull String username, @NonNull OrderCreateCommand command) {
        Optional<User> optionalUser = userRepository.findFirstByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        User user = optionalUser.get();

        List<Long> itemIds = command.getItems();
        Iterable<CartItem> cartItemIterable = cartItemRepository.findAllByUsernameAndIdIn(username, itemIds);
        List<CartItem> cartItems = IterableUtils.toList(cartItemIterable);
        if (cartItems.isEmpty()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }

        List<Long> foodIds = cartItems.stream().map(CartItem::getFoodId).collect(Collectors.toList());
        Iterable<Food> foodIterable = foodRepository.findAllByIdIn(foodIds);
        List<Food> foods = IterableUtils.toList(foodIterable);
        if (foods.isEmpty()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }

        Merchant merchant = foods.get(0).getMerchant();

        Order order = Order
                .builder()
                .orderTime(Instant.now())
                .status(Constant.OrderStatus.ACCEPTED)
                .address(command.getAddress())
                .user(user)
                .merchant(merchant)
                .build();

        double totalAmount = cartItems.stream().mapToDouble(c -> c.getQuantity() * c.getPrice()).sum();
        order.setTotalAmount(totalAmount);
        Order orderSave = orderRepository.save(order);

        Iterable<OrderItem> orderItems = cartItems.stream().map(item -> {
            Optional<Food> foodOptional = foodRepository.findById(item.getFoodId());
            return OrderItem.builder().quantity(item.getQuantity()).price(item.getPrice()).food(foodOptional.get()).order(orderSave).build();
        }).collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);
        cartItemRepository.deleteAll(cartItemIterable);

        return OrderView.from(orderSave);
    }


}
