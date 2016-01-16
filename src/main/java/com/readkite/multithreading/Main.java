package com.readkite.multithreading;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        //set default value of count 2 to increase probability of deadlock
        int count = args.length > 0 ? Integer.parseInt(args[0]) : 5;
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


        Thread.sleep(1*60*1000);

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
