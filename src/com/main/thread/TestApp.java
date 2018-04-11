package com.main.thread;

public class TestApp {
    public static void main(String[] args) {
        IPool pool = new IPoolImpl(10);
        pool.setHandler(new EventHandlerImpl());
        pool.start();
        pool.submit(new Event());
        pool.stop();
    }
}
