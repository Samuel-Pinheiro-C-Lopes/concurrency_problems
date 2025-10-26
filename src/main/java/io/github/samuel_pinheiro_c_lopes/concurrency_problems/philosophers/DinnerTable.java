package io.github.samuel_pinheiro_c_lopes.concurrency_problems.philosophers;

import java.util.concurrent.Semaphore;

public class DinnerTable {
    public final Semaphore[] forks;
    public final Semaphore seats;

    public DinnerTable(int seatsQuantity) {
        this.forks = new Semaphore[seatsQuantity + 1];
        for (int i = 0; i < seatsQuantity + 1; i++)
            this.forks[i] = new Semaphore(1, true);
        this.seats = new Semaphore(seatsQuantity, true);
    }
}


