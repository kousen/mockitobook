package com.kousenit.simple;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
public class LoggingWithOutputCaptureTest {
    @Test
    void testLogWithOutputCapture(CapturedOutput outputCapture) {
        Logger logger = Logger.getLogger(LoggingDemoTests.class.getName());
        LoggingDemo demo = new LoggingDemo(logger);
        demo.doStuff("Hello, world!");

        assertThat(outputCapture.getOut())
                .contains("Doing useful stuff: Hello, world!");
        assertThat(outputCapture.getErr())
                .contains("INFO: Hello, world!");
    }
}
