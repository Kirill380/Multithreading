package com.redkite.multithreading;


import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MyPhilosopher extends Philosopher implements Runnable {

    public MyPhilosopher(int position, String name, Fork right, Fork left) {
        super(position, name, right, left);
    }

    private volatile boolean stopFlag;
    private long waitAnotherFork;

    @Override
    public void run() {
        boolean waitTooLong = false;
        while(!stopFlag) {

            if(!waitTooLong)
                think();
            else  {
                waitTooLong = false;
                sleepRandom(1000);
            }

            Equalizer.setWait(waitTime, position);
            if(Equalizer.needSleep(position)) {
                System.out.println("[Philosopher " + position + "] is too greedy, he need to share with others");
                sleepRandom(700);
            }


            left.lock.lock();
            System.out.println("[Philosopher " + position + "] took left fork");
            try {
                //figure out with InterruptedException
                if(right.lock.tryLock(10, TimeUnit.SECONDS)) {
                        //wrap with try-finally section?
                        System.out.println("[Philosopher " + position + "] took right fork");
                        eat();
                        right.lock.unlock();
                } else  {
                    waitTooLong = true;
                    System.out.println("[Philosopher " + position + "] waiting too long, he dropped left fork." );
                    left.lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!waitTooLong)
                left.lock.unlock();

        }
        System.out.println("[Philosopher " + position + "] has finished");
    }

    public boolean isStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(boolean stopFlag) {
        this.stopFlag = stopFlag;
    }


    private void sleepRandom(int range) {
        try {
            Thread.sleep(new Random().nextInt(range));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
