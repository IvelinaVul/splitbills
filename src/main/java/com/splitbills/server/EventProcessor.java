package com.splitbills.server;

import java.util.concurrent.BlockingQueue;

public class EventProcessor implements Runnable {

    private final BlockingQueue<Task> newTasks;
    private final BlockingQueue<Task> completedTasks;

    public EventProcessor(BlockingQueue<Task> newTasks, BlockingQueue<Task> completedTasks) {
        this.newTasks = newTasks;
        this.completedTasks = completedTasks;
    }

    @Override
    public void run() {

    }

    public void stop() {

    }
}
