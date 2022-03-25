package com.kousenit.pubsub;

public class NamedSubscriber implements Subscriber {
    private final String name;

    public NamedSubscriber(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void receive(String message) {
        System.out.println(name + " received: " + message);
    }
}
