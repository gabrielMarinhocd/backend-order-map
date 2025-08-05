package com.gabrielmsantos.ordermap.domain.repository;

import com.gabrielmsantos.ordermap.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
