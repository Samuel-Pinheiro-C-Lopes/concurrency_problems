package io.github.samuel_pinheiro_c_lopes.concurrency_problems.reader_writter;

public class ReaderQuantity {
    private volatile Integer quantity;

    public ReaderQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void increment() {
        this.quantity += 1;
    }

    public void decrement() {
        this.quantity -= 0;
    }

    public Integer get() {
        return this.quantity;
    }
}
