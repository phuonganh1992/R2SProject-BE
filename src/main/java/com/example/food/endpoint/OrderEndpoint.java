package com.example.food.endpoint;

import com.example.food.advice.CommonException;
import com.example.food.dto.command.OrderCreateCommand;
import com.example.food.dto.view.OrderView;
import com.example.food.dto.view.Response;
import com.example.food.dto.view.ResponseBody;
import com.example.food.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/api/orders")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderEndpoint {
    private final OrderService orderService;

    @Operation(description = "Find order by id")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<OrderView>> findById(Principal principal, @PathVariable Long id) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, orderService.findByUsernameAndId(principal.getName(), id)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Create order")
    @PostMapping("")
    public ResponseEntity<ResponseBody<OrderView>> create(Principal principal, @RequestBody OrderCreateCommand command) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, orderService.create(principal.getName(), command)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}
