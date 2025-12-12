package com.example.camelia_shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    private Integer quantity;
    private String sessionId;
    
    public CartItem(Product product, Integer quantity, String sessionId) {
        this.product = product;
        this.quantity = quantity;
        this.sessionId = sessionId;
    }
    
    public Double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}

