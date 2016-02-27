package com.redkite.multithreding.webgraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;



public class SearchRobot {
    private String initialUrl = "http://www.terrabank.ua/";
    private int depth = 4;
    private int counter = 1;
    private double ratio = 0.5;

    public static void main(String[] args) throws  ExecutionException, InterruptedException {
        SearchRobot robot = new SearchRobot();
        robot.parseInput(args);
        robot.startSearching();
    }

    //TODO refactor main loop
    private void startSearching() throws  ExecutionException, InterruptedException {
        int numOfThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService fetchers = Executors.newFixedThreadPool(numOfThreads / 2);
        ExecutorService finders = Executors.newFixedThreadPool(numOfThreads / 2);

        Future<FilePiece> f = fetchers.submit(new URLFetcherTask(new Address(initialUrl)));
        Future<Set<Address>> f2 = finders.submit(new FinderTask(f.get()));
        System.out.println("Site: " + initialUrl + " Level: " + 0);

        Set<Address> addresses = f2.get();

        while (counter <= depth) {

            List<Future<FilePiece>> fetchersFutures = new ArrayList<>();
            List<Future<Set<Address>>> finderFutures = new ArrayList<>();

            for (Address address : addresses) {
                if(!address.isVisited()) {
                    address.visited();
                    System.out.println("Site: " + address.getAddress() + " Level: " + counter);
                    fetchersFutures.add(fetchers.submit(new URLFetcherTask(address)));
                }
            }

            for (Future<FilePiece> future : fetchersFutures) {
                finderFutures.add(fetchers.submit(new FinderTask(future.get())));
            }

            for (Future<Set<Address>> finderFuture : finderFutures) {
                addresses.addAll(finderFuture.get());
            }

            counter++;
        }

        fetchers.shutdown();
        finders.shutdown();

    }


    private void parseInput(String[] args) {
        for (int i = 0; i < args.length; i++) {
            // detect option if no options were detected then ignore program arguments
            if (args[i].contains("-")) {
                if (args.length <= i + 1)
                    throw new IllegalArgumentException("Option may not be without value");
                switch (args[i]) {
                    case "-url":
                        initialUrl = args[i + 1];
                        break;
                    case "-d":
                    case "-depth":
                        depth = parseIntValue(args[i + 1]);
                        break;
                    case "-ratio":
                    case "-r":
                        ratio = parseIntValue(args[i + 1])/100;
                        break;
                    default:
                        throw new IllegalArgumentException("No such option");
                }
            }
        }

    }


    private int parseIntValue(String value) {
        int num = Integer.parseInt(value);
        if (num <= 0)
            throw new IllegalArgumentException("Value may not be less or equal zero");
        return num;
    }
}
