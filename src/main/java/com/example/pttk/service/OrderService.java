package com.example.pttk.service;
import com.example.pttk.model.CartItem;
import com.example.pttk.model.Order;
import com.example.pttk.model.OrderItem;
import com.example.pttk.model.User;
import com.example.pttk.repository.OrderItemRepository;
import com.example.pttk.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartService cartService;

    public Order placeOrder(User user, String address, String paymentMethod) {
        List<CartItem> cartItems = cartService.getCartItems(user);

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty. Cannot place an order.");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setShippingAddress(address);
        order.setPaymentMethod(paymentMethod);
        order.setStatus("PENDING");

        double totalAmount = cartService.calculateTotalAmount(cartItems);
        order.setTotalAmount(totalAmount);

        order = orderRepository.save(order);

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItemRepository.save(orderItem);
        }

        cartService.clearCart(user); // Clear the cart after placing the order

        return order;
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}