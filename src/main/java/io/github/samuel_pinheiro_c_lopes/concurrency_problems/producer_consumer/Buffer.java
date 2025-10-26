package io.github.samuel_pinheiro_c_lopes.concurrency_problems.producer_consumer;

import java.util.concurrent.Semaphore;
import java.util.ArrayList;

public class Buffer {
    // Critical zone
    public final ArrayList<Integer> values;
    // Semaphores
    public final Semaphore mutex;
    public final Semaphore full;
    public final Semaphore empty;
    
    public Buffer(int bufferSize) {
    	this.values = new ArrayList<Integer>(bufferSize);
        this.mutex = new Semaphore(1, true);
        this.full = new Semaphore(0, true);
        this.empty = new Semaphore(bufferSize, true);
    }
}



