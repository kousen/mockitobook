package com.kousenit.pubsub;

public interface Subscriber {
    void onNext(String message);
}
