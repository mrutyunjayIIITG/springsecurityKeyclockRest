package com.mrutyu.springbootkeycloakrestu.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrutyu.springbootkeycloakrestu.entity.Order;
import com.mrutyu.springbootkeycloakrestu.entity.OrderItem;
import com.mrutyu.springbootkeycloakrestu.repository.OrderItemRepository;
import com.mrutyu.springbootkeycloakrestu.repository.OrderRepository;

@RestController
@RequestMapping("/order")
@SecurityRequirement(name = "Keycloak")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @GetMapping("/{restaurantId}/list")
    // manager can access (suresh)
    public List<Order> getOrders(@PathVariable Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    @GetMapping("/{orderId}")
    // manager can access (suresh)
    public Order getOrderDetails(@PathVariable Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setOrderItems(orderItemRepository.findByOrderId(order.getId()));
        }
        return order;
    }

    @PostMapping
    // authenticated users can access
    public Order createOrder(Order order) {
        orderRepository.save(order);
        List<OrderItem> orderItems = order.getOrderItems();
        if (orderItems != null) {
            orderItems.forEach(orderItem -> {
                orderItem.setOrderId(order.getId());
                orderItemRepository.save(orderItem);
            });
        }
        return order;
    }
}
