package com.kousenit.pubsub;

public interface Subscriber {
    void receive(String message);
    String getName();
}
