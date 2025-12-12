package com.example.camelia_shop;

import com.example.camelia_shop.model.Product;
import com.example.camelia_shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/collections")
    public String collections() {
        return "collections";
    }

    @GetMapping("/categories")
    public String categories() {
        return "categories";
    }

    @GetMapping("/accessories")
    public String accessories() {
        return "accessories";
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "shop";
    }

    @GetMapping("/lookbook")
    public String lookbook() {
        return "lookbook";
    }

    @GetMapping("/contacts")
    public String contacts() {
        return "contacts";
    }
}

