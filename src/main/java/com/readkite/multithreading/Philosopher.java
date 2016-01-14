package com.readkite.multithreading;


import java.util.Random;

public class Philosopher {
    protected int position;
    protected String name;
    protected final Fork left;
    protected final Fork right;
    protected int eatCount;
    protected long waitTime;
    protected long startWait;
    private Random rnd = new Random();
    private final int RANGE = 500;


    public Philosopher(int position, String name, Fork right, Fork left) {
        this.position = position;
        this.name = name;
        this.right = right;
        this.left = left;
    }

    public void eat() {
        waitTime += System.currentTimeMillis() - startWait;
        System.out.println("[Philosopher " + position + "] is eating");
        try {
            Thread.sleep(10/*rnd.nextInt(RANGE)*/);
        } catch (InterruptedException e) { e.printStackTrace(); }
        eatCount++;
        System.out.println("[Philosopher " + position + "] has finished eating");
    }

    public void think() {
        System.out.println("[Philosopher " + position + "] is thinking");
        try {
            Thread.sleep(10/*rnd.nextInt(RANGE)*/);
        } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("[Philosopher " + position + "] is hungry");
        startWait = System.currentTimeMillis();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEatCount() {
        return eatCount;
    }

    public void setEatCount(int eatCount) {
        this.eatCount = eatCount;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }
}
