package com.example.pttk.repository;


import com.example.pttk.model.CartItem;
import com.example.pttk.model.Product;
import com.example.pttk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    CartItem findByUserAndProduct(User user, Product product);
}