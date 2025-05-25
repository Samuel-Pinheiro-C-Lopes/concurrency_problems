package io.github.samuel_pinheiro_c_lopes.concurrency_problems.producer_consumer;

import java.util.concurrent.Semaphore;

import java.util.ArrayList;

import java.lang.InterruptedException;
import java.lang.Thread;
import java.lang.Runnable;

public class Buffer {
    // Critical zone
    public final ArrayList<Integer> values;
    // For the critical zone
    public final Semaphore mutex;
    public final Semaphore full;
    public final Semaphore empty;
    private volatile boolean running = true;
    private int bufferSize;

    public Buffer(int bufferSize) {
        this.mutex = new Semaphore(1, true);
        this.full = new Semaphore(0, true);
        this.empty = new Semaphore(bufferSize, true);
        this.values = new ArrayList<Integer>();
        this.bufferSize = bufferSize;
    }
}
