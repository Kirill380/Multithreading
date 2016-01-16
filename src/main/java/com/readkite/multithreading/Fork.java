package com.readkite.multithreading;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {

    public final Lock lock = new ReentrantLock();
}
