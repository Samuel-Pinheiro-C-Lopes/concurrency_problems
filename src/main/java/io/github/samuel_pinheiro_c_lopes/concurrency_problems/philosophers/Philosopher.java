package io.github.samuel_pinheiro_c_lopes.concurrency_problems.philosophers;

import java.lang.Runnable;
import java.lang.InterruptedException;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Philosopher implements Runnable {
    private int id;
    private Semaphore[] forks;
    private Semaphore seats;
    private volatile boolean running = true;
    private Random random;

    public Philosopher(int id, Semaphore[] forks, Semaphore seats) {
        this.random = new Random();
        this.id = id;
        this.forks = forks;
        this.seats = seats;
    }

    @Override
    public void run() {
        while (this.running) {
            try {
                this.seats.acquire();
                this.think();
                this.forks[this.id].acquire();
                this.forks[(this.id + 1)%this.forks.length].acquire();
                this.eat();
                this.forks[this.id].release();
                this.forks[(this.id + 1)%this.forks.length].release();
                this.think();
                this.seats.release();
                this.think();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void think() throws InterruptedException {
        System.out.println(this.id + " is thinking.");
        TimeUnit.MILLISECONDS.sleep(25 + this.random.nextInt(200));
    }

    private void eat() throws InterruptedException {
        System.out.println(this.id + " is eating.");
        TimeUnit.MILLISECONDS.sleep(25 + this.random.nextInt(200));
    }

    public void stop() {
        this.running = false;
    }
}
