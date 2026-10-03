package com.codtech.ecommerce.repository;

import com.codtech.ecommerce.entity.Cart;
import com.codtech.ecommerce.entity.CartItem;
import com.codtech.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart(Cart cart);

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}