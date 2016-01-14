package com.readkite.multithreading;


public class MyPhilosopher extends Philosopher implements Runnable {

    public MyPhilosopher(int position, String name, Fork right, Fork left) {
        super(position, name, right, left);
    }

    private volatile boolean stopFlag;

    @Override
    public void run() {
        while(!stopFlag) {
            think();
            synchronized (left) {
                System.out.println("[Philosopher " + position + "] took left fork");
                synchronized (right) {
                    System.out.println("[Philosopher " + position + "] took right fork");
                    eat();
                }
            }

        }
        System.out.println("[Philosopher " + position + "] has finished");
    }

    public boolean isStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(boolean stopFlag) {
        this.stopFlag = stopFlag;
    }


}
