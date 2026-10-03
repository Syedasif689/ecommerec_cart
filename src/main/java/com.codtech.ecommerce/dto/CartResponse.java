package com.codtech.ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartResponse {

    private Long cartId;
    private String customerId;
    private List<CartItemResponse> items;
    private BigDecimal total;

    public CartResponse() {
    }

    public CartResponse(Long cartId, String customerId,
                        List<CartItemResponse> items, BigDecimal total) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.items = items;
        this.total = total;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<CartItemResponse> getItems() {
        return items;
    }

    public void setItems(List<CartItemResponse> items) {
        this.items = items;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public static class CartItemResponse {

        private Long productId;
        private String productName;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal subtotal;

        public CartItemResponse() {
        }

        public CartItemResponse(Long productId, String productName,
                                BigDecimal price, Integer quantity,
                                BigDecimal subtotal) {
            this.productId = productId;
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
            this.subtotal = subtotal;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(BigDecimal subtotal) {
            this.subtotal = subtotal;
        }
    }
}