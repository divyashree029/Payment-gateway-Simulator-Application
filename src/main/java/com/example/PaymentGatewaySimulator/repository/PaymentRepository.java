package com.example.PaymentGatewaySimulator.repository;

import com.example.PaymentGatewaySimulator.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
