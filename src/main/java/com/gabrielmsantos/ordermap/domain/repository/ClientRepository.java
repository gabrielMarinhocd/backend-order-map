package com.gabrielmsantos.ordermap.domain.repository;

import com.gabrielmsantos.ordermap.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
