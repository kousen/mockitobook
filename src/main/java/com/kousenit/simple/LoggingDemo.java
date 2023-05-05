package com.kousenit.simple;

import java.util.logging.Logger;

public class LoggingDemo {
    private final Logger logger;

    public LoggingDemo(Logger logger) {
        this.logger = logger;
    }

    public void doStuff(String message) {
        System.out.println("Doing useful stuff with: " + message);
        logger.info(message);
    }
}
