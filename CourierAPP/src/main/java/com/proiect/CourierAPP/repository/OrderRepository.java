package com.proiect.CourierAPP.repository;

import com.proiect.CourierAPP.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
