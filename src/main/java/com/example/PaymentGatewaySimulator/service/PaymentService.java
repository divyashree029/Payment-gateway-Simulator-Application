package com.example.PaymentGatewaySimulator.service;

import com.example.PaymentGatewaySimulator.dto.PaymentRequest;
import com.example.PaymentGatewaySimulator.dto.PaymentResponse;
import com.example.PaymentGatewaySimulator.entity.Payment;
import com.example.PaymentGatewaySimulator.enums.PaymentStatus;
import com.example.PaymentGatewaySimulator.exception.IdempotencyConflictException;
import com.example.PaymentGatewaySimulator.exception.PaymentNotFoundException;
import com.example.PaymentGatewaySimulator.mapper.PaymentMapper;
import com.example.PaymentGatewaySimulator.repository.PaymentRepository;
import com.example.PaymentGatewaySimulator.util.RequestFingerprintGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // ============================================================
    // CREATE PAYMENT
    // ============================================================

    public PaymentResponse createPayment(
            PaymentRequest request) {

        String requestFingerprint =
                RequestFingerprintGenerator.generate(request);

        Payment existingPayment =
                paymentRepository
                        .findByIdempotencyKey(requestFingerprint)
                        .orElse(null);

        if (existingPayment != null) {

            if (!existingPayment.getRequestFingerprint()
                    .equals(requestFingerprint)) {

                throw new IdempotencyConflictException(
                        "Idempotency-Key already used with a different request"
                );
            }

            return PaymentMapper.toResponse(existingPayment);
        }

        Payment payment =
                PaymentMapper.toEntity(request);

        payment.setIdempotencyKey(requestFingerprint);

        payment.setRequestFingerprint(requestFingerprint);

        payment.setTransactionId(
                "PAY-" + UUID.randomUUID()
        );

        payment.setPaymentStatus(
                PaymentStatus.INITIATED
        );

        Payment savedPayment =
                paymentRepository.save(payment);

        return PaymentMapper.toResponse(savedPayment);
    }


    // ============================================================
    // GET PAYMENT BY ID
    // ============================================================

    public PaymentResponse getPaymentById(Long id) {

        Payment payment =
                paymentRepository.findById(id)
                        .orElseThrow(
                                () -> new PaymentNotFoundException(
                                        "Payment not found with id: " + id
                                )
                        );

        return PaymentMapper.toResponse(payment);
    }


    // ============================================================
    // GET ALL PAYMENTS - PAGINATED
    // ============================================================

    public Page<PaymentResponse> getAllPayments(
            Pageable pageable) {

        return paymentRepository
                .findAll(pageable)
                .map(PaymentMapper::toResponse);
    }


    // ============================================================
    // UPDATE PAYMENT STATUS
    // ============================================================

    public PaymentResponse updatePaymentStatus(
            Long id,
            PaymentStatus status) {

        Payment payment =
                paymentRepository.findById(id)
                        .orElseThrow(
                                () -> new PaymentNotFoundException(
                                        "Payment not found with id: " + id
                                )
                        );

        payment.setPaymentStatus(status);

        Payment updatedPayment =
                paymentRepository.save(payment);

        return PaymentMapper.toResponse(updatedPayment);
    }
}