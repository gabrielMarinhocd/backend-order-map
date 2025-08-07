package com.gabrielmsantos.ordermap.domain.repository;

import com.gabrielmsantos.ordermap.domain.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
