package com.br.mssankhyaorderconsumer.infrastructure.repository;

import com.br.mssankhyaorderconsumer.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
