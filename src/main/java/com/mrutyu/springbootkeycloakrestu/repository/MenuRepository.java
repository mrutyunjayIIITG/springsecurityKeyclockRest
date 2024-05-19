package com.mrutyu.springbootkeycloakrestu.repository;

import com.mrutyu.springbootkeycloakrestu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository  extends JpaRepository<Menu, Long> {

    Menu findByRestaurantId(Long restaurantId);
}
