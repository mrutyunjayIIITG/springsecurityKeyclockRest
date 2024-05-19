package com.mrutyu.springbootkeycloakrestu.repository;


import com.mrutyu.springbootkeycloakrestu.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findAllByMenuId(Long id);

}
