package com.kousenit.pubsub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

class PublisherTest {
    private final Publisher pub = new Publisher();
    private final Subscriber sub1 = mock(Subscriber.class);
    private final Subscriber sub2 = mock(Subscriber.class);

    @BeforeEach
    void setUp() {
        pub.subscribe(sub1);
        pub.subscribe(sub2);
    }

    @Test
    void publisherSendsMessageToAllSubscribers() {
        pub.send("Hello");

        verify(sub1).onNext("Hello");
        verify(sub2).onNext("Hello");
    }

    @Test
    void handleMisbehavingSubscribers() {
        // Does not compile (because onNext returns void):
        // when(sub1.onNext(anyString())).thenThrow(new RuntimeException("Oops"));

        // sub1 throws an exception
        doThrow(RuntimeException.class).when(sub1).onNext(anyString());
        doNothing().when(sub2).onNext(anyString()); // legal, but redundant (it's the default)

        pub.send("message 1");
        pub.send("message 2");

        // both subscribers still received the messages
        verify(sub1, times(2)).onNext(matches("message \\d"));
        verify(sub2, times(2)).onNext(anyString());
    }

    @Test
    void testSendInOrder() {  // Is this a good idea?? Overspecifying the implementation
        pub.send("Hello");

        InOrder inorder = inOrder(sub1, sub2);
        inorder.verify(sub1).onNext("Hello");
        inorder.verify(sub2).onNext("Hello");
    }

    @Test @DisplayName("Test send in parallel")
    @Disabled("This test fails because the order of the calls is not guaranteed")
    void testSendParallelCausesProblemsWithOrder() {
        pub.sendParallel("Hello");

        InOrder inorder = inOrder(sub1, sub2);
        inorder.verify(sub1).onNext("Hello");
        inorder.verify(sub2).onNext("Hello");
    }
    
    @Test
    void testSendParallel_withoutOrderVerification() {
        pub.sendParallel("Hello");
        
        // Verify both subscribers received the message (without order verification)
        verify(sub1).onNext("Hello");
        verify(sub2).onNext("Hello");
    }
    
    @Test
    void testSendParallel_withException() {
        // Configure sub1 to throw an exception
        doThrow(RuntimeException.class).when(sub1).onNext(anyString());
        
        // Call should not throw the exception outside the method
        pub.sendParallel("Message");
        
        // Verify both subscribers were called
        verify(sub1).onNext("Message");
        verify(sub2).onNext("Message");
    }

    @Test
    void publisherSendsMessageWithAPattern() {
        pub.send("Message 1");
        pub.send("Message 2");

        // Check for any string
        verify(sub1, times(2)).onNext(anyString());
        verify(sub2, times(2)).onNext(anyString());

        // Check for specific string pattern
        verify(sub1, times(2)).onNext(
                argThat(s -> s.matches("Message \\d")));
        verify(sub2, times(2)).onNext(
                argThat(s -> s.matches("Message [1-2]")));

        // Simpler, without custom matcher
        verify(sub1, times(2)).onNext(matches("Message \\d"));
    }

}
