package com.lallantop.repository;

import org.springframework.data.repository.CrudRepository;

import com.lallantop.domain.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}
