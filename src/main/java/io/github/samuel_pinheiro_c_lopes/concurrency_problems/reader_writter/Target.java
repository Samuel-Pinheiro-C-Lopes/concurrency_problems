package io.github.samuel_pinheiro_c_lopes.concurrency_problems.reader_writter;

import java.util.concurrent.Semaphore;

public class Target {
    public ReaderQuantity readersQuantity;
    public final Semaphore mutex;
    public final Semaphore wr_mutex;

    public Target() {
        this.mutex = new Semaphore(1, true);
        this.wr_mutex = new Semaphore(1, true);
        this.readersQuantity = new ReaderQuantity(0);
    }
}
