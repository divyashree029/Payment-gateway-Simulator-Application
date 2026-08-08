package com.example.PaymentGatewaySimulator.service;

import com.example.PaymentGatewaySimulator.dto.RefundRequest;
import com.example.PaymentGatewaySimulator.dto.RefundResponse;
import com.example.PaymentGatewaySimulator.entity.Payment;
import com.example.PaymentGatewaySimulator.entity.Refund;
import com.example.PaymentGatewaySimulator.enums.PaymentStatus;
import com.example.PaymentGatewaySimulator.enums.RefundStatus;
import com.example.PaymentGatewaySimulator.exception.PaymentNotFoundException;
import com.example.PaymentGatewaySimulator.exception.RefundNotAllowedException;
import com.example.PaymentGatewaySimulator.mapper.RefundMapper;
import com.example.PaymentGatewaySimulator.repository.PaymentRepository;
import com.example.PaymentGatewaySimulator.repository.RefundRepository;
import com.example.PaymentGatewaySimulator.util.RefundIdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class RefundService {

    private final PaymentRepository paymentRepository;
    private final RefundRepository refundRepository;
    private final RefundIdGenerator refundIdGenerator;

    public RefundService(
            PaymentRepository paymentRepository,
            RefundRepository refundRepository,
            RefundIdGenerator refundIdGenerator) {

        this.paymentRepository = paymentRepository;
        this.refundRepository = refundRepository;
        this.refundIdGenerator = refundIdGenerator;
    }

    @Transactional
    public RefundResponse createRefund(
            Long paymentId,
            RefundRequest request) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Payment not found with id: " + paymentId
                        ));

        if (payment.getPaymentStatus() != PaymentStatus.SUCCESS) {

            throw new RefundNotAllowedException(
                    "Only successful payments can be refunded"
            );
        }

        BigDecimal alreadyRefunded =
                refundRepository.sumSuccessfulRefundsByPaymentId(
                        paymentId
                );

        BigDecimal remainingRefundable =
                payment.getAmount().subtract(alreadyRefunded);

        if (request.getAmount().compareTo(remainingRefundable) > 0) {

            throw new RefundNotAllowedException(
                    "Refund amount exceeds refundable amount. " +
                            "Remaining refundable amount: "
                            + remainingRefundable
            );
        }

        Refund refund = Refund.builder()
                .refundId(refundIdGenerator.generateRefundId())
                .payment(payment)
                .amount(request.getAmount())
                .refundStatus(RefundStatus.SUCCESS)
                .build();

        Refund savedRefund =
                refundRepository.save(refund);

        if (request.getAmount()
                .compareTo(remainingRefundable) == 0) {

            payment.setPaymentStatus(
                    PaymentStatus.REFUNDED
            );

            paymentRepository.save(payment);
        }

        return RefundMapper.toResponse(savedRefund);
    }
}