package com.vuongideas.gamum.processor.messaging;

public interface Consumer<T> {
    public void onMessage(byte[] message);
}
