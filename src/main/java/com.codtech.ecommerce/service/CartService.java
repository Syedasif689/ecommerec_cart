package com.codtech.ecommerce.service;

import com.codtech.ecommerce.dto.AddToCartRequest;
import com.codtech.ecommerce.entity.Cart;
import com.codtech.ecommerce.entity.Product;
import com.codtech.ecommerce.entity.CartItem;
import com.codtech.ecommerce.exception.CartNotFoundException;
import com.codtech.ecommerce.exception.InsufficientStockException;
import com.codtech.ecommerce.exception.ProductNotFoundException;
import com.codtech.ecommerce.repository.CartItemRepository;
import com.codtech.ecommerce.repository.CartRepository;
import com.codtech.ecommerce.repository.ProductRepository;
import com.codtech.ecommerce.dto.UpdateCartItemRequest;
import com.codtech.ecommerce.dto.CartResponse;
import com.codtech.ecommerce.exception.CartItemNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       ProductRepository productRepository) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public Cart getCartByCustomerId(String customerId) {

        return cartRepository.findByCustomerId(customerId)
                .orElseThrow(() ->
                        new CartNotFoundException(
                                "Cart not found for customer: " + customerId
                        )
                );
    }

    public Cart addToCart(AddToCartRequest request) {

        Cart cart = cartRepository.findByCustomerId(request.getCustomerId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomerId(request.getCustomerId());
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id: "
                                        + request.getProductId()
                        )
                );

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(null);

        int newQuantity;

        if (cartItem == null) {

            newQuantity = request.getQuantity();

            if (newQuantity > product.getStock()) {
                throw new InsufficientStockException(
                        "Insufficient stock for product: "
                                + product.getName()
                                + ". Available stock: "
                                + product.getStock()
                );
            }

            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(newQuantity);

        } else {

            newQuantity =
                    cartItem.getQuantity() + request.getQuantity();

            if (newQuantity > product.getStock()) {
                throw new InsufficientStockException(
                        "Insufficient stock for product: "
                                + product.getName()
                                + ". Available stock: "
                                + product.getStock()
                );
            }

            cartItem.setQuantity(newQuantity);
        }

        cartItemRepository.save(cartItem);

        return cart;
    }
    public Cart updateCartItem(Long cartItemId,
                           UpdateCartItemRequest request) {

    CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() ->
                    new CartItemNotFoundException(
                            "Cart item not found with id: " + cartItemId
                    )
            );

    Product product = cartItem.getProduct();

    if (request.getQuantity() > product.getStock()) {
        throw new InsufficientStockException(
                "Insufficient stock for product: "
                        + product.getName()
                        + ". Available stock: "
                        + product.getStock()
        );
    }

    cartItem.setQuantity(request.getQuantity());

    cartItemRepository.save(cartItem);

    return cartItem.getCart();
}
 public Cart removeCartItem(Long cartItemId) {

    CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() ->
                    new CartItemNotFoundException(
                            "Cart item not found with id: " + cartItemId
                    )
            );

    Cart cart = cartItem.getCart();

    cartItemRepository.delete(cartItem);

    return cart;
}
public CartResponse getCartResponse(String customerId) {

    Cart cart = getCartByCustomerId(customerId);

    var items = cartItemRepository.findByCart(cart)
            .stream()
            .map(cartItem -> {

                Product product = cartItem.getProduct();

                var subtotal = product.getPrice()
                        .multiply(
                                java.math.BigDecimal.valueOf(
                                        cartItem.getQuantity()
                                )
                        );

                return new CartResponse.CartItemResponse(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        cartItem.getQuantity(),
                        subtotal
                );
            })
            .toList();

    var total = items.stream()
            .map(CartResponse.CartItemResponse::getSubtotal)
            .reduce(
                    java.math.BigDecimal.ZERO,
                    java.math.BigDecimal::add
            );

    return new CartResponse(
            cart.getId(),
            cart.getCustomerId(),
            items,
            total
    );
}
}