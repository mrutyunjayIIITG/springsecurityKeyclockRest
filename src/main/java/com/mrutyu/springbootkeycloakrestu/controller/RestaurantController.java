package com.mrutyu.springbootkeycloakrestu.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrutyu.springbootkeycloakrestu.entity.Menu;
import com.mrutyu.springbootkeycloakrestu.entity.MenuItem;
import com.mrutyu.springbootkeycloakrestu.entity.Restaurant;
import com.mrutyu.springbootkeycloakrestu.repository.MenuItemRepository;
import com.mrutyu.springbootkeycloakrestu.repository.MenuRepository;
import com.mrutyu.springbootkeycloakrestu.repository.RestaurantRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/restaurant")
@SecurityRequirement(name = "Keycloak")
public class RestaurantController {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    MenuItemRepository menuItemRepository;

    @GetMapping("/public/list")
//@GetMapping("/list")   // for test remove it
    //Public API
    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    @GetMapping("/public/menu/{restaurantId}")
    //Public API
    public Menu getMenu(@PathVariable Long restaurantId) {
        Menu menu = menuRepository.findByRestaurantId(restaurantId);
        if (menu != null) {
            menu.setMenuItems(menuItemRepository.findAllByMenuId(menu.getId()));
        }
        return menu;
    }

    @PostMapping
    // admin can access (admin)
    @PreAuthorize("hasRole('admin')")
    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @PostMapping("/menu")
    // manager can access (suresh)
    @PreAuthorize("hasRole('manager')")
    public Menu createMenu(Menu menu) {
        menuRepository.save(menu);
        if (menu.getMenuItems() != null) {
            menu.getMenuItems().forEach(menuItem -> {
                menuItem.setMenuId(menu.getId());
                menuItemRepository.save(menuItem);
            });
        }
        return menu;
    }

    @PutMapping("/menu/item/{itemId}/{price}")
    // owner can access (amar)
    @PreAuthorize("hasRole('owner')")
    public MenuItem updateMenuItemPrice(@PathVariable Long itemId
            , @PathVariable BigDecimal price) {
        Optional<MenuItem> menuItem = menuItemRepository.findById(itemId);
        if (menuItem.isPresent()) {
            menuItem.get().setPrice(price);
            menuItemRepository.save(menuItem.get());
            return menuItem.get();
        } else {
            return null;
        }
    }


    // my test

        @GetMapping("/adminaccsess")
//        @PreAuthorize("hasRole('admin')")
        @PreAuthorize("hasRole('admin')")
        public String adminEndpoint() {
            return "Hello Admin";
        }












}
