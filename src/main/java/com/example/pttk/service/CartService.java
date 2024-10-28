package com.example.pttk.service;

import com.example.pttk.model.CartItem;
import com.example.pttk.model.Product;
import com.example.pttk.model.User;
import com.example.pttk.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> getCartItems(User user) {
        return cartItemRepository.findByUser(user);
    }

    public void addCartItem(User user, Product product, int quantity) {
        CartItem existingItem = cartItemRepository.findByUserAndProduct(user, product);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem(user, product, quantity);
            cartItemRepository.save(cartItem);
        }
    }

    public void updateCartItemQuantity(Long itemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(itemId).orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }

    public void removeCartItem(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }

    public double calculateTotalAmount(List<CartItem> cartItems) {
        return cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    public void clearCart(User user) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        cartItemRepository.deleteAll(cartItems);
    }
}