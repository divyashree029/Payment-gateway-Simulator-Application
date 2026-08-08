package com.example.PaymentGatewaySimulator.service;

import com.example.PaymentGatewaySimulator.dto.PaymentRequest;
import com.example.PaymentGatewaySimulator.dto.PaymentResponse;
import com.example.PaymentGatewaySimulator.entity.Payment;
import com.example.PaymentGatewaySimulator.enums.PaymentStatus;
import com.example.PaymentGatewaySimulator.exception.InvalidPaymentStateException;
import com.example.PaymentGatewaySimulator.exception.PaymentNotFoundException;
import com.example.PaymentGatewaySimulator.mapper.PaymentMapper;
import com.example.PaymentGatewaySimulator.repository.PaymentRepository;
import com.example.PaymentGatewaySimulator.util.TransactionIdGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Payment not found with id : " + id
                        ));
        return PaymentMapper.toResponse(payment);
    }

    public Page<PaymentResponse> getAllPayments(Pageable pageable) {
        Page<Payment> payments = paymentRepository.findAll(pageable);
        return payments.map(PaymentMapper::toResponse);
    }
    public PaymentResponse updatePaymentStatus(Long id, PaymentStatus newStatus) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new PaymentNotFoundException("Payment not found with id: " + id));
        PaymentStatus currentStatus = payment.getPaymentStatus();

        boolean validTransition = (currentStatus == PaymentStatus.INITIATED && newStatus == PaymentStatus.PROCESSING)
                        || (currentStatus == PaymentStatus.PROCESSING && (newStatus == PaymentStatus.SUCCESS
                        || newStatus == PaymentStatus.FAILED))
                        || (currentStatus == PaymentStatus.SUCCESS && newStatus == PaymentStatus.REFUNDED);

        if (!validTransition) {
            throw new InvalidPaymentStateException(
                    "Invalid payment state transition: " + currentStatus + " -> " + newStatus);
        }
        payment.setPaymentStatus(newStatus);
        Payment updatedPayment = paymentRepository.save(payment);
        return PaymentMapper.toResponse(updatedPayment);
    }

}
