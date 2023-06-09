package com.kousenit.inorder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private PaymentService paymentServiceMock;

    @Mock
    private ShippingService shippingServiceMock;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void processOrder() {
        // set the expectations on the mocks
        when(paymentServiceMock.processPayment(anyDouble())).thenReturn(true);
        when(shippingServiceMock.shipProduct(anyString())).thenReturn(true);

        // call the method to test
        double amount = 100.00;
        String address = "1313 Mockingbird Lane, New York, NY 10001";
        assertTrue(orderService.processOrder(amount, address));

        // verify that the mocked methods were called in the right order
        InOrder inOrder = inOrder(paymentServiceMock, shippingServiceMock);
        inOrder.verify(paymentServiceMock).processPayment(amount);
        inOrder.verify(shippingServiceMock).shipProduct(address);
    }
}
