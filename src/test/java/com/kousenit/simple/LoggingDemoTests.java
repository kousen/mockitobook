package com.kousenit.simple;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class LoggingDemoTests {
    private final Logger logger =
            Logger.getLogger(LoggingDemoTests.class.getName());

    @Test
    public void testLogWithSpy() {
        // Spy on the logger
        Logger spy = spy(logger);

        // Inject the logger into the class under test
        LoggingDemo demo = new LoggingDemo(spy);

        // Invoke the method to test
        demo.doStuff("Hello, world!");

        // Verify that the info method of the logger was called
        verify(spy).info("Hello, world!");
    }

    @Test
    public void testLog() {
        LoggingDemo demo = new LoggingDemo(logger);
        assertDoesNotThrow(
                () -> demo.doStuff("Hello, world!"));

        // Nothing to test, because both
        //      doStuff and info methods return void
    }


}
