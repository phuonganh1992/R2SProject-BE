package com.example.food.endpoint;

import com.example.food.advice.CommonException;
import com.example.food.dto.view.CartView;
import com.example.food.dto.view.Response;
import com.example.food.dto.view.ResponseBody;
import com.example.food.service.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/api/carts")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CartEndpoint {
    private final CartService cartService;

    @Operation(description = "Show cart of user")
    @GetMapping("")
    public ResponseEntity<ResponseBody<CartView>> showCart(Principal principal) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, cartService.showCart(principal.getName())), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Plus food in cart")
    @PostMapping("/plus/{foodId}")
    public ResponseEntity<ResponseBody<CartView>> plusFoodInCart(Principal principal, @PathVariable("foodId") Long foodId) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, cartService.plusFoodInCart(principal.getName(), foodId)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }

    @Operation(description = "Minus food in cart")
    @PostMapping("/minus/{foodId}")
    public ResponseEntity<ResponseBody<CartView>> minusFoodInCart(Principal principal, @PathVariable("foodId") Long foodId) {
        try {
            return new ResponseEntity<>(new ResponseBody(Response.SUCCESS, cartService.minusFoodInCart(principal.getName(), foodId)), HttpStatus.OK);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ResponseBody(Response.OBJECT_NOT_FOUND, null), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()), HttpStatus.OK);
        }
    }
}
