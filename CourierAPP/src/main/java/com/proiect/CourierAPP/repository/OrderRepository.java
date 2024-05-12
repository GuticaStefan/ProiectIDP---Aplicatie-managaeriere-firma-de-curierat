package com.proiect.CourierAPP.repository;

import com.proiect.CourierAPP.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Set<Order> findOrdersByUserId(UUID userId);

}
