package com.kousenit.simple;

import java.util.logging.Logger;

public class LoggingDemo {
    private final Logger logger;

    public LoggingDemo(Logger logger) {
        this.logger = logger;
    }

    public void doStuff(String message) {
        System.out.printf("Doing useful stuff: %s%n", message);
        logger.info(message);
    }
}
