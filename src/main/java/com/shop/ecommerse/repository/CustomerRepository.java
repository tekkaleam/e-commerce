package com.shop.ecommerse.repository;

import com.shop.ecommerse.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
