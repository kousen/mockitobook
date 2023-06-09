package com.kousenit.inorder;

public class PaymentService {
    // Dependency of the class under test
    private final PaymentProcessor processor;

    // Constructor to make it easy to inject the dependency
    public PaymentService(PaymentProcessor processor) {
        this.processor = processor;
    }

    // Need to test this method
    public boolean processPayment(double amount) {
        // Payment must be authorized before collecting the money
        if (processor.authorizePayment(amount)) {
            return processor.capturePayment();
        }
        return false;
    }
}
