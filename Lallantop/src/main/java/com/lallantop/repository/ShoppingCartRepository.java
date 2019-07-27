package com.lallantop.repository;

import org.springframework.data.repository.CrudRepository;

import com.lallantop.domain.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

}
