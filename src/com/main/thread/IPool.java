package com.main.thread;

public interface IPool {
    void start();

    void stop();

    void submit(Event event);

    void setHandler(EventHandler handler);
}
