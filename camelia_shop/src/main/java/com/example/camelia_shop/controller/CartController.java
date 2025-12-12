package com.example.camelia_shop.controller;

import com.example.camelia_shop.model.CartItem;
import com.example.camelia_shop.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        String sessionId = session.getId();
        List<CartItem> items = cartService.getCartItems(sessionId);
        Double total = cartService.getTotal(sessionId);
        
        model.addAttribute("items", items);
        model.addAttribute("total", total);
        return "cart";
    }
    
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity,
            HttpSession session) {
        
        String sessionId = session.getId();
        try {
            cartService.addToCart(sessionId, productId, quantity);
            List<CartItem> items = cartService.getCartItems(sessionId);
            Double total = cartService.getTotal(sessionId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Товар добавлен в корзину");
            response.put("cartCount", items.size());
            response.put("total", total);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Ошибка: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateQuantity(
            @RequestParam Long itemId,
            @RequestParam Integer quantity,
            HttpSession session) {
        
        String sessionId = session.getId();
        try {
            cartService.updateQuantity(sessionId, itemId, quantity);
            List<CartItem> items = cartService.getCartItems(sessionId);
            Double total = cartService.getTotal(sessionId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("cartCount", items.size());
            response.put("total", total);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Ошибка: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/remove")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> removeFromCart(
            @RequestParam Long itemId,
            HttpSession session) {
        
        String sessionId = session.getId();
        try {
            cartService.removeFromCart(sessionId, itemId);
            List<CartItem> items = cartService.getCartItems(sessionId);
            Double total = cartService.getTotal(sessionId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Товар удален из корзины");
            response.put("cartCount", items.size());
            response.put("total", total);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Ошибка: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/count")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCartCount(HttpSession session) {
        String sessionId = session.getId();
        List<CartItem> items = cartService.getCartItems(sessionId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("count", items.size());
        response.put("total", cartService.getTotal(sessionId));
        
        return ResponseEntity.ok(response);
    }
}

