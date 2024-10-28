package com.example.pttk.controller;


import com.example.pttk.model.CartItem;
import com.example.pttk.model.Product;
import com.example.pttk.model.User;
import com.example.pttk.service.CartService;
import com.example.pttk.service.ProductService;
import com.example.pttk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewCart(Model model) {
        Optional<User> user = userService.findUserById(1L);
        List<CartItem> cartItems = cartService.getCartItems(user.orElse(null));
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalAmount", cartService.calculateTotalAmount(cartItems));
        return "cart";
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId, @RequestParam int quantity) {
        Optional<Product> product = productService.getProductById(productId);
        Optional<User> user = userService.findUserById(1L);
        cartService.addCartItem(user.orElse(null), product.orElse(null), quantity);
        return "redirect:/cart";
    }

    @PostMapping("/update/{itemId}")
    public String updateCartItem(@PathVariable Long itemId, @RequestParam int quantity) {
        cartService.updateCartItemQuantity(itemId, quantity);
        return "redirect:/cart";
    }

    @DeleteMapping("/remove/{itemId}")
    public String removeCartItem(@PathVariable Long itemId) {
        cartService.removeCartItem(itemId);
        return "redirect:/cart";
    }
}