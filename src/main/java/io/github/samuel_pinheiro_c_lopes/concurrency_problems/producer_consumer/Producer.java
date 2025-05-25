package io.github.samuel_pinheiro_c_lopes.concurrency_problems.producer_consumer;

import java.util.concurrent.Semaphore;

import java.lang.Runnable;
import java.lang.InterruptedException;

import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {
    private ArrayList<Integer> values;
    private Random random;
    private Semaphore mutex;
    private Semaphore full;
    private Semaphore empty;
    private volatile boolean running = true;
    private int lastProducedValue;

    public Producer(ArrayList<Integer> values, Semaphore mutex, Semaphore full, Semaphore empty) {
        this.random = new Random();
        this.mutex = mutex;
        this.full = full;
        this.empty = empty;
        this.values = values;
        this.lastProducedValue = -1;
    }

    @Override
    public void run() {
        try {
            while(this.running) {
                this.empty.acquire();
                this.mutex.acquire();
                this.lastProducedValue = this.random.nextInt(10) * this.random.nextInt(10);
                System.out.println("I just produced: " + this.lastProducedValue + ".");
                this.values.add(this.lastProducedValue);
                this.mutex.release();
                this.full.release();
                TimeUnit.MILLISECONDS.sleep(100 + this.random.nextInt(400));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.running = false;
    }
}
