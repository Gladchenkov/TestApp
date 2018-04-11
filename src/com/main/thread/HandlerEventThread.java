package com.main.thread;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class HandlerEventThread extends Thread {
    private AtomicBoolean isBusy = new AtomicBoolean(false);
    private EventHandler eventHandler;
    private ConcurrentLinkedQueue<Event> queue;

    public HandlerEventThread(EventHandler eventHandler, String name) {
        super(name);
        this.queue = new ConcurrentLinkedQueue<>();
        this.isBusy = new AtomicBoolean(false);
        this.eventHandler = eventHandler;
    }

    public void addEvent(Event event) {
        queue.add(event);
    }

    public boolean isExecuting() {
        return isBusy.get();
    }

    @Override
    public void run() {
        while (true) {
            //check queue every 5 sec
            if (!queue.isEmpty()) {
                isBusy.set(true);
                System.out.println("From " + super.getName());
                eventHandler.handle(queue.poll());
                isBusy.set(false);
            } else {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
