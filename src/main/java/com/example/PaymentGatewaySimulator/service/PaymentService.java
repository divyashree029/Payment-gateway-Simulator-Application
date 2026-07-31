package com.example.PaymentGatewaySimulator.service;

import com.example.PaymentGatewaySimulator.dto.PaymentRequest;
import com.example.PaymentGatewaySimulator.dto.PaymentResponse;
import com.example.PaymentGatewaySimulator.entity.Payment;
import com.example.PaymentGatewaySimulator.enums.PaymentStatus;
import com.example.PaymentGatewaySimulator.mapper.PaymentMapper;
import com.example.PaymentGatewaySimulator.repository.PaymentRepository;
import com.example.PaymentGatewaySimulator.util.TransactionIdGenerator;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final TransactionIdGenerator transactionIdGenerator;

    public PaymentService(
            PaymentRepository paymentRepository,
            TransactionIdGenerator transactionIdGenerator) {

        this.paymentRepository = paymentRepository;
        this.transactionIdGenerator = transactionIdGenerator;
    }

    /*public Payment createPayment(Payment payment){
       payment.setPaymentStatus(PaymentStatus.INITIATED);
       return paymentRepository.save(payment);
    }*/

    public PaymentResponse createPayment(PaymentRequest request) {
        Payment payment = PaymentMapper.toEntity(request);
        payment.setTransactionId(
                transactionIdGenerator.generateTransactionId()
        );
        payment.setPaymentStatus(PaymentStatus.INITIATED);
        Payment savedPayment = paymentRepository.save(payment);
        return PaymentMapper.toResponse(savedPayment);
    }


}
