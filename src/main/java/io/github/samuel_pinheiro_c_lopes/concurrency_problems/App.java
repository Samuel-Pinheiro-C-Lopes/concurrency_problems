package io.github.samuel_pinheiro_c_lopes.concurrency_problems;
// For the producer consumer problem
import io.github.samuel_pinheiro_c_lopes.concurrency_problems.producer_consumer.Buffer;
import io.github.samuel_pinheiro_c_lopes.concurrency_problems.producer_consumer.Consumer;
import io.github.samuel_pinheiro_c_lopes.concurrency_problems.producer_consumer.Producer;
// For the philosophers problem
import io.github.samuel_pinheiro_c_lopes.concurrency_problems.philosophers.Philosopher;
import io.github.samuel_pinheiro_c_lopes.concurrency_problems.philosophers.DinnerTable;
// For the reader writter problem
import io.github.samuel_pinheiro_c_lopes.concurrency_problems.reader_writter.Reader;
import io.github.samuel_pinheiro_c_lopes.concurrency_problems.reader_writter.Writter;
import io.github.samuel_pinheiro_c_lopes.concurrency_problems.reader_writter.Target;

import java.lang.Thread;
import java.lang.InterruptedException;

import java.util.concurrent.TimeUnit;

public class App {
    private static int bufferSize = 10;
    private static int seatsSize = 5;
    private static int timeRunning = 3;

    public static void main(String[] args) throws Exception {
        App.testProducerConsumer();
        App.testPhilosophers();
        App.testReadersWritters();
        System.out.println("TESTS FINISHED!!!"); // end of program
    }

    // I think that the writters are going to starve a lot... (I was right ^^) ~ need to
    // solve it latter
    protected static void testReadersWritters() {
        Target target = new Target();
        // Readers
        Reader r1 = new Reader(0, target.mutex, target.wr_mutex, target.readersQuantity);
        Reader r2 = new Reader(1, target.mutex, target.wr_mutex, target.readersQuantity);
        Reader r3 = new Reader(2, target.mutex, target.wr_mutex, target.readersQuantity);
        Reader r4 = new Reader(3, target.mutex, target.wr_mutex, target.readersQuantity);
        Reader r5 = new Reader(4, target.mutex, target.wr_mutex, target.readersQuantity);
        Thread r1Thread = new Thread(r1);
        Thread r2Thread = new Thread(r2);
        Thread r3Thread = new Thread(r3);
        Thread r4Thread = new Thread(r4);
        Thread r5Thread = new Thread(r5);
        // Writters
        Writter w1 = new Writter(0, target.wr_mutex);
        Writter w2 = new Writter(1, target.wr_mutex);
        Writter w3 = new Writter(2, target.wr_mutex);
        Thread w1Thread = new Thread(w1);
        Thread w2Thread = new Thread(w2);
        Thread w3Thread = new Thread(w3);
        try {
            // start readers
            r1Thread.start();
            r2Thread.start();
            r3Thread.start();
            r4Thread.start();
            r5Thread.start();
            // start writters
            w1Thread.start();
            w2Thread.start();
            w3Thread.start();
            TimeUnit.SECONDS.sleep(App.timeRunning); // running for 10 seconds
            // stop readers
            r1.stop();
            r2.stop();
            r3.stop();
            r4.stop();
            r5.stop();
            // stop writters
            w1.stop();
            w2.stop();
            w3.stop();
            // let readers finish
            r1Thread.join();
            r2Thread.join();
            r3Thread.join();
            r4Thread.join();
            r5Thread.join();
            // let writters finish
            w1Thread.join();
            w2Thread.join();
            w3Thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected static void testPhilosophers() {
        DinnerTable table = new DinnerTable(App.seatsSize);
        // philosophers
        Philosopher p1 = new Philosopher(0, table.mutex, table.forks, table.seats);
        Philosopher p2 = new Philosopher(1, table.mutex, table.forks, table.seats);
        Philosopher p3 = new Philosopher(2, table.mutex, table.forks, table.seats);
        Philosopher p4 = new Philosopher(3, table.mutex, table.forks, table.seats);
        Philosopher p5 = new Philosopher(4, table.mutex, table.forks, table.seats);
        Thread p1Thread = new Thread(p1);
        Thread p2Thread = new Thread(p2);
        Thread p3Thread = new Thread(p3);
        Thread p4Thread = new Thread(p4);
        Thread p5Thread = new Thread(p5);
        try {
            // start philosophers
            p1Thread.start();
            p2Thread.start();
            p3Thread.start();
            p4Thread.start();
            p5Thread.start();
            TimeUnit.SECONDS.sleep(App.timeRunning);
            // stop philosophers
            p1.stop();
            p2.stop();
            p3.stop();
            p4.stop();
            p5.stop();
            // wait for philosophers to finish
            p1Thread.join();
            p2Thread.join();
            p3Thread.join();
            p4Thread.join();
            p5Thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected static void testProducerConsumer() {
        // buffer
        Buffer buffer = new Buffer(App.bufferSize);
        // Thread bufferThread = new Thread(buffer);
        // producers
        Producer p1 = new Producer(buffer.values, buffer.mutex, buffer.full, buffer.empty);
        Producer p2 = new Producer(buffer.values, buffer.mutex, buffer.full, buffer.empty);
        Producer p3 = new Producer(buffer.values, buffer.mutex, buffer.full, buffer.empty);
        Producer p4 = new Producer(buffer.values, buffer.mutex, buffer.full, buffer.empty);
        Thread p1Thread = new Thread(p1);
        Thread p2Thread = new Thread(p2);
        Thread p3Thread = new Thread(p3);
        Thread p4Thread = new Thread(p4);
        // consumers
        Consumer c1 = new Consumer(buffer.values, buffer.mutex, buffer.full, buffer.empty);
        Consumer c2 = new Consumer(buffer.values, buffer.mutex, buffer.full, buffer.empty);
        Consumer c3 = new Consumer(buffer.values, buffer.mutex, buffer.full, buffer.empty);
        Consumer c4 = new Consumer(buffer.values, buffer.mutex, buffer.full, buffer.empty);
        Thread c1Thread = new Thread(c1);
        Thread c2Thread = new Thread(c2);
        Thread c3Thread = new Thread(c3);
        Thread c4Thread = new Thread(c4);
        try {
            // start producers
            p1Thread.start();
            p2Thread.start();
            p3Thread.start();
            p4Thread.start();
            // start consumers
            c1Thread.start();
            c2Thread.start();
            c3Thread.start();
            c4Thread.start();
            TimeUnit.SECONDS.sleep(App.timeRunning); // running for 10 seconds
            // stop producers
            p1.stop();
            p2.stop();
            p3.stop();
            p4.stop();
            // stop consumers
            c1.stop();
            c2.stop();
            c3.stop();
            c4.stop();
            // wait for producers to finish
            p1Thread.join();
            p2Thread.join();
            p3Thread.join();
            p4Thread.join();
            // wait for consumers to finish
            c1Thread.join();
            c2Thread.join();
            c3Thread.join();
            c4Thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
