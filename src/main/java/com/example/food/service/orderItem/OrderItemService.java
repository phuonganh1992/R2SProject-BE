package com.example.food.service.orderItem;

import com.example.food.advice.CommonException;
import com.example.food.domain.Category;
import com.example.food.domain.OrderItem;
import com.example.food.dto.view.OrderItemView;
import com.example.food.dto.view.Response;
import com.example.food.repository.IOrderItemRepository;
import lombok.NonNull;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemService {
    @Autowired
    private IOrderItemRepository orderItemRepository;

    public List<OrderItemView> findAllByOrderId(@NonNull String username, @NonNull Long id) {

        Iterable<OrderItem> orderItemIterable = orderItemRepository.findAllByOrderId(id);

        List<OrderItem> orderItems = IterableUtils.toList(orderItemIterable);

        if (orderItems.isEmpty()) {
            throw new CommonException(Response.OBJECT_NOT_FOUND, Response.OBJECT_NOT_FOUND.getResponseMessage());
        }
        List<OrderItemView> items = orderItems.stream().map(OrderItemView::from).collect(Collectors.toList());
        return items;
    }
}
