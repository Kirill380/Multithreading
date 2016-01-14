package com.readkite.multithreading;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int count = args.length > 1 ? Integer.parseInt(args[1]) : 2;
        MyPhilosopher[] philosophers = new MyPhilosopher[count];
        Thread[] threads =  new Thread[count];

        Fork[] forks = new Fork[count];
        for (int i = 0; i < count; i++) {
            forks[i] = new Fork();
        }

        for (int i = 0; i < count; i++) {
            philosophers[i] = new MyPhilosopher(i, "unnamed", forks[i], forks[(i + 1)%(count)]);
        }


        for (int i = 0; i < count; i++) {
            threads[i] = new Thread(philosophers[i]);
            threads[i].start();
        }


        Thread.sleep(5*60*1000);

        for (MyPhilosopher philosopher : philosophers) {
            philosopher.setStopFlag(true);
        }

        for (Thread thread : threads) {
            thread.join();
        }

        for (int i = 0; i < 50; i++) out.print("=");
        out.println("\n");

        for (MyPhilosopher philosopher : philosophers) {
            out.println(String.format("[Philosopher %d] ate %d times and waited %d ms", philosopher.getPosition(),
                                                                                      philosopher.getEatCount(),
                                                                                      philosopher.getWaitTime()));
        }

    }
}
