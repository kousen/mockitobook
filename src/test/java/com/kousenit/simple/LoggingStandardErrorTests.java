package com.kousenit.simple;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

public class LoggingStandardErrorTests {
    @Test
    void testLogWithStnErr() {
        Logger logger = Logger.getLogger(LoggingDemoTests.class.getName());
        LoggingDemo demo = new LoggingDemo(logger);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();

        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));

        demo.doStuff("Hello, world!");

        assertThat(out.toString()).contains("Doing useful stuff with: Hello, world!");
        assertThat(err.toString()).contains("INFO: Hello, world!");

        System.setOut(System.out);
        System.setErr(System.err);
    }

}
