package com.example.food.endpoint;

import com.example.food.advice.CommonException;
import com.example.food.dto.view.OrderItemView;
import com.example.food.dto.view.Response;
import com.example.food.dto.view.ResponseBody;
import com.example.food.service.orderItem.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/api/items")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderItemEndpoint {
    private final OrderItemService orderItemService;

    @Operation(description = "Find order items by id of order")
    @GetMapping("")
    public ResponseEntity<ResponseBody<OrderItemView>> findById(Principal principal, @RequestParam("orderId") Long orderId) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, orderItemService.findAllByOrderId(principal.getName(), orderId)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}
