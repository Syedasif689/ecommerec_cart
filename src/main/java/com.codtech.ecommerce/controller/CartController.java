package com.codtech.ecommerce.controller;

import com.codtech.ecommerce.dto.AddToCartRequest;
import com.codtech.ecommerce.dto.CartResponse;
import com.codtech.ecommerce.dto.UpdateCartItemRequest;
import com.codtech.ecommerce.entity.Cart;
import com.codtech.ecommerce.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

   @PostMapping("/add")
public ResponseEntity<CartResponse> addToCart(
        @Valid @RequestBody AddToCartRequest request) {

    Cart cart = cartService.addToCart(request);

    CartResponse response =
            cartService.getCartResponse(cart.getCustomerId());

    return ResponseEntity.ok(response);
}

    @GetMapping("/{customerId}")
    public ResponseEntity<CartResponse> getCart(
            @PathVariable String customerId) {

        CartResponse response =
                cartService.getCartResponse(customerId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponse> updateCartItem(
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequest request) {

        Cart cart = cartService.updateCartItem(
                cartItemId,
                request
        );

        CartResponse response =
                cartService.getCartResponse(cart.getCustomerId());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<CartResponse> removeCartItem(
            @PathVariable Long cartItemId) {

        Cart cart = cartService.removeCartItem(cartItemId);

        CartResponse response =
                cartService.getCartResponse(cart.getCustomerId());

        return ResponseEntity.ok(response);
    }
}