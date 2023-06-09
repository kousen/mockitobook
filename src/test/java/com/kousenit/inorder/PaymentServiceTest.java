package com.kousenit.inorder;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Test
    void processPayment() {
        // create the mock
        PaymentProcessor processor = mock(PaymentProcessor.class);

        // set expectations on the mock
        when(processor.authorizePayment(anyDouble())).thenReturn(true);
        when(processor.capturePayment()).thenReturn(true);

        // inject the mock into the class under test
        PaymentService service = new PaymentService(processor);

        // call method under test
        assertTrue(service.processPayment(100.0));

        // verify the methods on the mock were called in the right order
        InOrder inOrder = inOrder(processor);
        inOrder.verify(processor).authorizePayment(anyDouble());
        inOrder.verify(processor).capturePayment();
    }
}