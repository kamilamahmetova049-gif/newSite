package com.example.camelia_shop.controller;

import com.example.camelia_shop.model.CartItem;
import com.example.camelia_shop.model.Order;
import com.example.camelia_shop.model.OrderItem;
import com.example.camelia_shop.repository.OrderRepository;
import com.example.camelia_shop.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        String sessionId = session.getId();
        List<CartItem> items = cartService.getCartItems(sessionId);
        Double total = cartService.getTotal(sessionId);
        
        if (items.isEmpty()) {
            return "redirect:/cart";
        }
        
        model.addAttribute("items", items);
        model.addAttribute("total", total);
        return "checkout";
    }
    
    @PostMapping("/create")
    public String createOrder(
            @RequestParam String customerName,
            @RequestParam String customerEmail,
            @RequestParam String customerPhone,
            @RequestParam String address,
            @RequestParam String city,
            @RequestParam String paymentMethod,
            HttpSession session,
            Model model) {
        
        String sessionId = session.getId();
        List<CartItem> cartItems = cartService.getCartItems(sessionId);
        
        if (cartItems.isEmpty()) {
            model.addAttribute("error", "Корзина пуста");
            return "redirect:/cart";
        }
        
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setCustomerEmail(customerEmail);
        order.setCustomerPhone(customerPhone);
        order.setAddress(address);
        order.setCity(city);
        order.setPaymentMethod(paymentMethod);
        order.setTotalAmount(cartService.getTotal(sessionId));
        order.setStatus("PAID");
        
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem(
                cartItem.getProduct().getName(),
                cartItem.getProduct().getPrice(),
                cartItem.getQuantity()
            );
            orderItem.setOrder(order);
            order.getItems().add(orderItem);
        }
        
        orderRepository.save(order);
        cartService.clearCart(sessionId);
        
        model.addAttribute("order", order);
        return "order-success";
    }
}

