package com.codtech.ecommerce.repository;

import com.codtech.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByCustomerId(String customerId);
}