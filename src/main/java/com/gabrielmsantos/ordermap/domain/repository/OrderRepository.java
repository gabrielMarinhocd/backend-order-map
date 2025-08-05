package com.gabrielmsantos.ordermap.domain.repository;

import com.gabrielmsantos.ordermap.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
