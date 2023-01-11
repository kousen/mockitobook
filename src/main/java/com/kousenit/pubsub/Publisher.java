package com.kousenit.pubsub;

import java.util.ArrayList;
import java.util.List;

// Adapted from a similar example in the Spock framework
public class Publisher {
    private final List<Subscriber> subscribers = new ArrayList<>();

    public void subscribe(Subscriber sub) {
        subscribers.add(sub);
    }

    // Want to test this method
    public void send(String message) {
        for (Subscriber sub : subscribers) {
            try {
                sub.onNext(message);
            } catch (Exception ignored) {
                // evil, but what can you do?
            }
        }
    }

    public void sendParallel(String message) {
        subscribers.parallelStream().forEach(sub -> {
            try {
                sub.onNext(message);
            } catch (Exception ignored) {
                // evil, but what can you do?
            }
        });
    }

}

