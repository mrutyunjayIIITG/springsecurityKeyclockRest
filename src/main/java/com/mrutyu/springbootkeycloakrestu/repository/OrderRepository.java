package com.mrutyu.springbootkeycloakrestu.repository;

import com.mrutyu.springbootkeycloakrestu.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByRestaurantId(Long restaurantId);

}