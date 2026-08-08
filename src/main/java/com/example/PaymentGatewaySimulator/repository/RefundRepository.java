package com.example.PaymentGatewaySimulator.repository;

import com.example.PaymentGatewaySimulator.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface RefundRepository extends JpaRepository<Refund, Long> {

    @Query("""
           SELECT COALESCE(SUM(r.amount), 0)
           FROM Refund r
           WHERE r.payment.id = :paymentId
           AND r.refundStatus = 'SUCCESS'
           """)
    BigDecimal sumSuccessfulRefundsByPaymentId(
            @Param("paymentId") Long paymentId
    );
}