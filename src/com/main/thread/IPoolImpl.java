package com.main.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class IPoolImpl implements IPool {

    private AtomicInteger poolSize;
    private EventHandler handler;
    private ConcurrentLinkedQueue<Event> eventQueue;
    private AtomicBoolean isExecuting;
    private List<HandlerEventThread> threads;

    public IPoolImpl(int size) {
        init(size);
    }

    private void init(int size) {
        poolSize = new AtomicInteger(size);
        eventQueue = new ConcurrentLinkedQueue<>();
        isExecuting = new AtomicBoolean(false);
        threads = new ArrayList<>(size);
    }

    public IPoolImpl() {
        init(2);
    }

    @Override
    public void start() {
        if (handler != null) {
            isExecuting.set(true);
            for (int i = 0; i < poolSize.get(); i++) {
                HandlerEventThread thread = new HandlerEventThread(handler, "Thread-" + i);
                thread.start();
                threads.add(thread);
                System.out.println("Thread " + thread.getName() + " is created and started");
            }
        } else {
            throw new IllegalStateException("EventHandler is not initialized");
        }
    }

    @Override
    public void stop() {
        if (isExecuting.get()) {
            eventQueue.clear();
            isExecuting.set(false);
            threads.forEach(Thread::interrupt);
        }
    }

    @Override
    public void submit(Event event) {
        if (isExecuting.get()) {
            if (event != null) {
                eventQueue.add(event);
                while (!eventQueue.isEmpty()) {
                    threads.stream()
                            .filter(thread -> !thread.isExecuting())
                            .findAny()
                            .get()
                            .addEvent(eventQueue.poll());
                }
            }
        } else {
            throw new IllegalStateException("ThreadPool has been terminated");
        }
    }

    @Override
    public void setHandler(EventHandler handler) {
        this.handler = handler;
    }
}
