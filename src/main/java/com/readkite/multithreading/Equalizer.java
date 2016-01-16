package com.readkite.multithreading;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Created by kyli0415 on 1/16/2016.
 */
public class Equalizer {
    // hardcoded
    private static long[] wait =  new long[5];

    private static final int ERROR = 300;
    public static synchronized void setWait(long waitTime, int position) {
        wait[position] = waitTime;
    }

    public static synchronized boolean needSleep(int position) {
        return wait[position] - Arrays.stream(wait).min().getAsLong() > ERROR;
    }



}
