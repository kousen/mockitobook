package com.kousenit.inorder;

public class OrderService {
    // Dependencies of the class under test
    private final PaymentService paymentService;
    private final ShippingService shippingService;

    // Constructor to make it easy to inject the dependencies
    public OrderService(PaymentService paymentService, ShippingService shippingService) {
        this.paymentService = paymentService;
        this.shippingService = shippingService;
    }

    // Need to test this method
    public boolean processOrder(double amount, String address) {
        if (paymentService.processPayment(amount)) {
            return shippingService.shipProduct(address);
        } else {
            return false;
        }
    }
}
