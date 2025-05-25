package io.github.samuel_pinheiro_c_lopes.concurrency_problems.reader_writter;

import java.lang.Runnable;
import java.lang.InterruptedException;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Reader implements Runnable {
    private final int id;
    private Semaphore mutex;
    private Semaphore wr_mutex;
    private ReaderQuantity readersQuantity;
    private volatile boolean running = true;
    private Random random;

    public Reader(int id, Semaphore mutex, Semaphore wr_mutex, ReaderQuantity readersQuantity) {
        this.random = new Random();
        this.mutex = mutex;
        this.wr_mutex = wr_mutex;
        this.readersQuantity = readersQuantity;
        this.id = id;
    }

    @Override
    public void run() {
        while (this.running) {
            try {
                this.mutex.acquire();
                    this.readersQuantity.increment();
                    if (this.readersQuantity.get() == 1)
                        this.wr_mutex.acquire();
                this.mutex.release();

                this.read();

                this.mutex.acquire();
                    this.readersQuantity.decrement();
                    if (this.readersQuantity.get() == 0)
                        this.wr_mutex.release();
                this.mutex.release();

                TimeUnit.MILLISECONDS.sleep(100 + random.nextInt(400));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (this.wr_mutex.availablePermits() == 0)
            this.wr_mutex.release();
    }

    private void read() throws InterruptedException {
        System.out.println(this.id + " is reading.");
        TimeUnit.MILLISECONDS.sleep(100 + random.nextInt(400));
    }

    public void stop() {
        this.running = false;
    }
}
