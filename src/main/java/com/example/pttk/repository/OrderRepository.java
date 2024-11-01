package com.example.pttk.repository;
import com.example.pttk.model.Order;
import com.example.pttk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}