package com.example.camelia_shop.service;

import com.example.camelia_shop.model.CartItem;
import com.example.camelia_shop.model.Product;
import com.example.camelia_shop.repository.CartItemRepository;
import com.example.camelia_shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<CartItem> getCartItems(String sessionId) {
        return cartItemRepository.findBySessionId(sessionId);
    }
    
    @Transactional
    public CartItem addToCart(String sessionId, Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        Optional<CartItem> existingItem = cartItemRepository.findBySessionIdAndProductId(sessionId, productId);
        
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            return cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem(product, quantity, sessionId);
            return cartItemRepository.save(newItem);
        }
    }
    
    @Transactional
    public void updateQuantity(String sessionId, Long itemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        if (!item.getSessionId().equals(sessionId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        if (quantity <= 0) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }
    }
    
    @Transactional
    public void removeFromCart(String sessionId, Long itemId) {
        CartItem item = cartItemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        if (!item.getSessionId().equals(sessionId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        cartItemRepository.delete(item);
    }
    
    @Transactional
    public void clearCart(String sessionId) {
        cartItemRepository.deleteBySessionId(sessionId);
    }
    
    public Double getTotal(String sessionId) {
        return getCartItems(sessionId).stream()
            .mapToDouble(CartItem::getTotalPrice)
            .sum();
    }
}

