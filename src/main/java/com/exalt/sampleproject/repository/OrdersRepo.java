package com.exalt.sampleproject.repository;

import com.exalt.sampleproject.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepo extends JpaRepository<Orders, Long> {
}
