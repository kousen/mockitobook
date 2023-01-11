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
    void testSendInOrder() {
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
    void publisherSendsMessageWithAPattern() {
        pub.send("Message 1");
        pub.send("Message 2");

        // Check for any string
        verify(sub1, times(2)).onNext(anyString());
        verify(sub2, times(2)).onNext(anyString());

        // Check for specific string pattern
        verify(sub1, times(2)).onNext(
                argThat(s -> s.matches("Message \\d")));
        verify(sub1, times(2)).onNext(
                argThat(s -> s.matches("Message \\d")));

        // Simpler, without custom matcher
        verify(sub1, times(2)).onNext(matches("Message \\d"));
    }

    @Test
    void handleMisbehavingSubscribers() {
        // Does not compile:
        // when(sub1.onNext(anyString())).thenThrow(new RuntimeException("Oops"));

        // sub1 throws an exception
        doThrow(RuntimeException.class).when(sub1).onNext(anyString());

        pub.send("message 1");
        pub.send("message 2");

        // both subscribers still received the messages
        verify(sub1, times(2)).onNext(matches("message \\d"));
        verify(sub2, times(2)).onNext(anyString());
    }
}
