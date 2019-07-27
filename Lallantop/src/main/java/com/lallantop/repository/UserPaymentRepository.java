package com.lallantop.repository;

import org.springframework.data.repository.CrudRepository;

import com.lallantop.domain.UserPayments;

public interface UserPaymentRepository extends CrudRepository<UserPayments, Long>{

}
