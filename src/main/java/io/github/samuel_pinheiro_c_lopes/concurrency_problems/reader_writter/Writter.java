package io.github.samuel_pinheiro_c_lopes.concurrency_problems.reader_writter;

import java.lang.Runnable;
import java.lang.InterruptedException;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Writter implements Runnable {
    private final int id;
    private Semaphore wr_mutex; // I dunno...
    private Random random;
    private volatile boolean running = true;

    public Writter(int id, Semaphore wr_mutex) {
        this.random = new Random();
        this.wr_mutex = wr_mutex;
        this.id = id;
    }

    @Override
    public void run() {
        while (this.running) {
            try {
                this.wr_mutex.acquire();
                this.write();
                this.wr_mutex.release();
                TimeUnit.MILLISECONDS.sleep(100 + this.random.nextInt(400));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void write() throws InterruptedException {
        System.out.println(this.id + " is writting.");
        TimeUnit.MILLISECONDS.sleep(100 + this.random.nextInt(400));
    }

    public void stop() {
        this.running = false;
    }
}
