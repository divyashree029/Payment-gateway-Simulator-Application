package com.example.PaymentGatewaySimulator.util;

import com.example.PaymentGatewaySimulator.dto.PaymentRequest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RequestFingerprintGenerator {

    public static String generate(PaymentRequest request) {

        String data =
                request.getAmount() + "|" +
                        request.getPaymentMethod();

        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash =
                    digest.digest(
                            data.getBytes(StandardCharsets.UTF_8)
                    );

            StringBuilder hexString =
                    new StringBuilder();

            for (byte b : hash) {
                String hex =
                        Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "SHA-256 algorithm not available",
                    e
            );
        }
    }
}