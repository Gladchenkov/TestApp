package com.main.thread;

public class EventHandlerImpl implements EventHandler {
    @Override
    public void handle(Event event) {
        event.sayHello();
    }
}
