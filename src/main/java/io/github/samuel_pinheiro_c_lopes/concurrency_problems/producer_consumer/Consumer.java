package io.github.samuel_pinheiro_c_lopes.concurrency_problems.producer_consumer;

import java.util.concurrent.Semaphore;

import java.lang.Runnable;
import java.lang.InterruptedException;

import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
    private ArrayList<Integer> values;
    private ArrayList<Integer> consumedValues;
    private Random random;
    private Semaphore mutex;
    private Semaphore full;
    private Semaphore empty;
    private volatile boolean running = true;
    private int lastConsumedValue;

    public Consumer(ArrayList<Integer> values, Semaphore mutex, Semaphore full, Semaphore empty) {
        this.consumedValues = new ArrayList<Integer>();
        this.random = new Random();
        this.mutex = mutex;
        this.full = full;
        this.empty = empty;
        this.values = values;
        this.lastConsumedValue = -1;
    }

    @Override
    public void run() {
        try {
            while(this.running) {
                this.full.acquire();
                this.mutex.acquire();
                this.lastConsumedValue = this.values.remove(this.values.size() - 1);
                System.out.println("I just consumed: " + this.lastConsumedValue + ".");
                this.consumedValues.add(this.lastConsumedValue);
                this.mutex.release();
                this.empty.release();
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
