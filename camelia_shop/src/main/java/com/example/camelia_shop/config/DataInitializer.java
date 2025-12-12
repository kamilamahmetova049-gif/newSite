package com.example.camelia_shop.config;

import com.example.camelia_shop.model.Product;
import com.example.camelia_shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            String imageUrl = "https://images.unsplash.com/photo-1487412720507-e7ab37603c6f?auto=format&fit=crop&w=900&q=80";
            
            productRepository.save(new Product(
                "Пальто-кокон",
                "Кашемир 70%, цвет мокко",
                79000.0,
                "outerwear",
                imageUrl
            ));
            
            productRepository.save(new Product(
                "Сет жилет + брюки",
                "Графитовый твилл",
                31000.0,
                "outerwear",
                imageUrl
            ));
            
            productRepository.save(new Product(
                "Платье-комбинация",
                "Шёлк, цвет шампань",
                70000.0,
                "dresses",
                imageUrl
            ));
            
            productRepository.save(new Product(
                "Сумка-хобо",
                "Кожа наппа, песочный",
                46000.0,
                "accessories",
                imageUrl
            ));
            
            productRepository.save(new Product(
                "Комплект украшений",
                "Матовое серебро",
                61000.0,
                "accessories",
                imageUrl
            ));
            
            productRepository.save(new Product(
                "Ботильоны на kitten heel",
                "Натуральная кожа",
                70000.0,
                "shoes",
                imageUrl
            ));
            
            productRepository.save(new Product(
                "Тёплый трикотаж",
                "Мягкие кардиганы из альпаки и шерсти мериноса",
                39000.0,
                "knit",
                imageUrl
            ));
            
            productRepository.save(new Product(
                "Городские костюмы",
                "Структурированные жакеты и широкие брюки",
                60000.0,
                "outerwear",
                imageUrl
            ));
            
            productRepository.save(new Product(
                "Пальто Camelia",
                "Чёткие линии, глубокие оттенки и идеальная посадка",
                78000.0,
                "outerwear",
                imageUrl
            ));
        }
    }
}

