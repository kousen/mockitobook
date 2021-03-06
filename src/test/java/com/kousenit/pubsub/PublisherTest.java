package com.kousenit.pubsub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

class PublisherTest {
    private final Publisher pub = new Publisher();
    private final Subscriber sub1 = mock(Subscriber.class);
    private final Subscriber sub2 = mock(Subscriber.class);

    @BeforeEach
    void setUp() {
        when(sub1.getName()).thenReturn("sub1");
        when(sub2.getName()).thenReturn("sub2");
        pub.addSubscriber(sub1);
        pub.addSubscriber(sub2);
    }

    @Test
    void publisherSendsMessageToAllSubscribers() {
        pub.send("Hello");

        verify(sub1).receive("Hello");
        verify(sub2).receive("Hello");
    }

    @Test
    void testSendInOrder() {
        pub.send("Hello");

        InOrder inorder = inOrder(sub1, sub2);
        inorder.verify(sub1).receive("Hello");
        inorder.verify(sub2).receive("Hello");
    }

    @Test
    void publisherSendsMessageWithAPattern() {
        pub.send("Message 1");
        pub.send("Message 2");

        // Check for any string
        verify(sub1, times(2)).receive(anyString());
        verify(sub2, times(2)).receive(anyString());

        // Check for specific string pattern
        verify(sub1, times(2)).receive(
                argThat(s -> s.matches("Message \\d")));
        verify(sub1, times(2)).receive(
                argThat(s -> s.matches("Message \\d")));
    }

    @Test
    void handleMisbehavingSubscribers() {
        // sub1 throws an exception
        doThrow(RuntimeException.class).when(sub1).receive(anyString());

        pub.send("message 1");
        pub.send("message 2");

        // both subscribers still received the messages
        verify(sub1, times(2)).receive(anyString());
        verify(sub2, times(2)).receive(anyString());
    }
}
