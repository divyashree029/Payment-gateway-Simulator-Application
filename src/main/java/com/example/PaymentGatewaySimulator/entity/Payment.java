package com.example.PaymentGatewaySimulator.entity;

import com.example.PaymentGatewaySimulator.enums.PaymentMethod;
import com.example.PaymentGatewaySimulator.enums.PaymentStatus;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,unique=true)
    private String transactionId;

    @Column(nullable = false, unique = true, length = 100)
    private String idempotencyKey;

    @Column(nullable = false, length = 64)
    private String requestFingerprint;

    @Column(nullable=false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(updatable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
