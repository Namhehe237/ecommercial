package com.example.pttk.controller;

import com.example.pttk.model.Order;
import com.example.pttk.model.User;
import com.example.pttk.service.CartService;
import com.example.pttk.service.OrderService;
import com.example.pttk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @GetMapping
    public String viewOrders(Model model) {
        Optional<User> user = userService.findUserById(1L); // Dummy user
        List<Order> orders = orderService.getOrdersByUser(user.orElse(null));
        model.addAttribute("orders", orders);
        return "my_orders";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam String address, @RequestParam String paymentMethod) {
        Optional<User> user = userService.findUserById(1L); // Dummy user
        orderService.placeOrder(user.orElse(null), address, paymentMethod);
        cartService.clearCart(user.orElse(null));
        return "redirect:/orders";
    }

    @GetMapping("/{id}")
    public String viewOrderDetails(@PathVariable Long id, Model model) {
        Optional<Order> order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order_summary";
    }
}