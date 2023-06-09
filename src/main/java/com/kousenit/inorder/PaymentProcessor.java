package com.kousenit.inorder;

public interface PaymentProcessor {
    boolean authorizePayment(double amount);
    boolean capturePayment(); // request the authorized funds
}
